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
import yxl.UserAndTask.util.*;


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

    public int startTask(String tid) {
        /*Ut ut = localUt.getLocal(utid);
        if (ut == null) {
            ut = (Ut) redisUtil.get(utid);
        }
        if (ut == null) {
            ut = uts.findutbyId(utid);
        } else {
            localUt.addLocal(ut);
        }
        if (ut == null) {//3:任务不存在
            return 3;
        } else {
            redisUtil.setex(ut.getUt_id(), ut, 60 * 60);
            localUt.addLocal(ut);
        }*/
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

        Ut_working ut_working = new Ut_working(ut.getUt_id(), new Timestamp(new Date().getTime()));
        boolean ok = utw.insertut(ut_working);
        if (ok) {
            redisUtil.setex(String.valueOf(ut_working.getUtw_id()), ut_working, 60 * 60);
            return 0;
        } else {
            LogUtil.warn("任务执行时数据表入库失败");
        }
        return -1;
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
        }

        data.pushData();

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
