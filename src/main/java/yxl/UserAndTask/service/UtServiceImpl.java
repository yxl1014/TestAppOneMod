package yxl.UserAndTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataHandle.data.DirData;
import yxl.DataToMysql.util.TaskUtil;
import yxl.DataToMysql.util.UtUtil;
import yxl.DataToMysql.util.UtwUtil;
import yxl.DataToRedis.util.RedisUtil;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.entity.Ut;
import yxl.UserAndTask.entity.Ut_working;
import yxl.utils.*;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UtServiceImpl {

    @Autowired
    private UtUtil uts;

    @Autowired
    private UtwUtil utw;

    @Autowired
    private TaskUtil taskUtil;

    @Autowired
    private LocalTask localTask;

    @Autowired
    private LocalUt localUt;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DirData data;


    //1、准备中，2、开始，3、暂停，4、已取消，5、已完成
    public Task getTask(String tid, AtomicInteger ok) {
        Task t = localTask.getLocal(tid);
        if (t == null) {
            t = (Task) redisUtil.get(tid);
        }
        if (t == null) {
            t = taskUtil.findTaskbyId(tid);
        } else {
            localTask.addLocal(t);
        }
        if (t == null) {//3:任务不存在
            ok.set(3);
            return null;
        } else {
            redisUtil.setex(t.getT_id(), t, 60 * 60);
            localTask.addLocal(t);
        }

        if (!"开始".equals(t.getT_state())) {
            ok.set(4);//4:任务还不可以接受
            return null;
        }

        User u = TlUserUtil.getThreadLocal();
        Ut ut = new Ut(IdsUtil.getUtid(u.getU_id().substring(14), tid.substring(20)),
                u.getU_id(), tid, new Timestamp(new Date().getTime()), "暂停", 0);
        boolean ok1 = uts.insertut(ut);
        if (ok1) {
            ok.set(0);
            redisUtil.setex(ut.getUt_id(), ut, 60 * 60);
            localUt.addLocal(ut);
        } else {
            ok.set(-1);
            LogUtil.warn("任务用户映射表入库失败");
            return null;
        }
        return t;
    }

    public Ut_working startTask(String tid, AtomicInteger integer) {
        Task t = localTask.getLocal(tid);
        if (t == null) {
            t = (Task) redisUtil.get(tid);
        }
        if (t == null) {
            t = taskUtil.findTaskbyId(tid);
        } else {
            localTask.addLocal(t);
        }
        if (t == null) {//3:任务不存在
            integer.set(3);
            return null;
        } else {
            redisUtil.setex(t.getT_id(), t, 60 * 60);
            localTask.addLocal(t);
        }

        User u = TlUserUtil.getThreadLocal();

        Ut ut = uts.findutbyTidAndUid(tid, u.getU_id());
        if (ut == null) {//没有接受该任务
            integer.set(5);
            return null;
        }

        if (!"暂停".equals(ut.getUt_state())) {
            if ("结束".equals(ut.getUt_state())) {
                integer.set(7);//该任务以结束，请重新接受任务
            } else
                integer.set(6);//该任务测试中
            return null;
        }


        Ut_working ut_working = new Ut_working(ut.getUt_id(), new Timestamp(new Date().getTime()));
        boolean ok = utw.insertut(ut_working);
        if (ok) {
            ut_working = utw.findutwbyNew(ut_working);
            if (ut_working == null)
                return null;
            if (!uts.updateState("测试中", ut.getUt_id()))
                LogUtil.warn("Ut表,utid:" + ut.getUt_id() + ",该接受任务状态修改失败！");
            redisUtil.setex(String.valueOf(ut_working.getUtw_id()), ut_working, 60 * 60);
            integer.set(0);
            return ut_working;
        } else {
            LogUtil.warn("任务执行时数据表入库失败");
        }
        integer.set(-1);
        return null;
    }

    public int stopTask(String tid) {
        Task t = localTask.getLocal(tid);
        if (t == null) {
            t = (Task) redisUtil.get(tid);
        }
        if (t == null) {
            t = taskUtil.findTaskbyId(tid);
        } else {
            localTask.addLocal(t);
        }
        if (t == null) {//3:任务不存在
            return 3;
        } else {
            redisUtil.setex(t.getT_id(), t, 60 * 60);
            localTask.addLocal(t);
        }

        User u = TlUserUtil.getThreadLocal();

        Ut ut = uts.findutbyTidAndUid(tid, u.getU_id());
        if (ut == null) {//没有接受该任务
            return 5;
        }

        List<Ut_working> utws = utw.findNookTasks(ut.getUt_id());
        for (Ut_working w : utws) {
            utw.updateState(w.getUtw_id(), 0);
            if (!uts.updateState("暂停", ut.getUt_id()))
                LogUtil.warn("Ut表,utid:" + ut.getUt_id() + ",该接受任务状态修改失败！");
            if (!uts.updateResult(ut.getUt_allresult() + w.getUtw_result(), ut.getUt_id()))
                LogUtil.warn("数据没有入库成功");//修改任务接受任务表的总条数
            data.pushData(w);
        }
        return 0;
    }

    public List<Task> getcons_Tasks() {
        List<Task> tasks = new ArrayList<>();
        String uid = TlUserUtil.getThreadLocal().getU_id();
        List<Ut> utts = uts.findutbyUid(uid);
        for (Ut ut : utts) {
            tasks.add(taskUtil.findTaskbyId(ut.getUt_tid()));
        }
        return tasks;
    }
}
