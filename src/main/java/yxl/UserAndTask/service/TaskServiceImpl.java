package yxl.UserAndTask.service;


import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yxl.DataHandle.data.DirData;
import yxl.DataHandle.fileio.util.FileUtil;
import yxl.DataHandle.hadoop.hdfs.HadoopTemplate;
import yxl.DataToMysql.util.TaskUtil;
import yxl.DataToMysql.util.UtUtil;
import yxl.DataToMysql.util.UtwUtil;
import yxl.DataToRedis.util.RedisUtil;
import yxl.UserAndTask.entity.*;
import yxl.utils.IdsUtil;
import yxl.utils.LocalTask;
import yxl.utils.LogUtil;
import yxl.utils.TlUserUtil;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskServiceImpl {

    @Autowired
    private TaskUtil util;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UtUtil utUtil;

    @Autowired
    private UtwUtil utwUtil;

    @Autowired
    private DirData data;

    @Autowired
    private LocalTask localTable;

    @Autowired
    private HadoopTemplate hadoopTemplate;

    @Autowired
    private FileUtil fileUtil;

    @Value("${hadoop.out}")
    private String outUrl;

    @Value("${download.srcUrl}")
    private String downUrl;

    public List<Task> getAll() {
        List<Task> tasks = util.findTasks();
        localTable.addLocal(tasks);
        for (Task t : tasks) {
            redisUtil.setex(t.getT_name(), t, 60 * 60);
        }
        return tasks;
    }

    public List<Task> getAllbyUid() {
        User user = TlUserUtil.getThreadLocal();
        List<Task> tasks = util.findTasksbyUid(user.getU_id());
        localTable.addLocal(tasks);
        for (Task t : tasks) {
            redisUtil.setex(t.getT_name(), t, 60 * 60);
        }
        return tasks;
    }

    public Task maketask(@NonNull Task task) {
        User user = TlUserUtil.getThreadLocal();
        task.setT_id(IdsUtil.getTid(task.getT_type(), user.getU_id().substring(15)));
        task.setT_uid(user.getU_id());
        //task.setT_cost(0);
        task.setT_state("准备中");
        task.setT_stime(new Timestamp(new Date().getTime()));
        if (!util.insertTask(task)) {
            LogUtil.warn("任务入库失败重新录入！");
            return null;
        }
        if (!redisUtil.setex(task.getT_id(), task, 60 * 60)) {
            LogUtil.warn("新建任务没有存入缓存！");
        }
        localTable.addLocal(task);
        return task;
    }

    public int updateState(@NonNull String tid, String state) {//3：没有该任务
        if (!util.containsTid(tid))//查询这个任务是否存在
            return 3;
        if (!util.updateTask(tid, state)) {//修改任务状态
            return -1;
        }
        Task n = util.findTaskbyId(tid);//得到这个任务
        if (!redisUtil.setex(n.getT_id(), n, 60 * 60)) {//将任务存入redis
            LogUtil.warn("新建任务没有存入缓存！");
        }
        localTable.addLocal(n);//将任务存入本地内存
        if (!"开始".equals(state)) {//判断状态类型，若不是开始,则需要停止所有测试中任务
            List<Ut> uts = utUtil.findutbyTid(tid);//找到所有接受这个任务的用户
            for (Ut ut : uts) {//遍历
                List<Ut_working> utws = utwUtil.findNookTasks(ut.getUt_id());//查找所有还没有执行完任务
                if (utws.size() == 0)
                    continue;
                int result = 0;//请求成功总条数
                for (Ut_working utw : utws) {//遍历
                    data.pushData(utw);//清空数据接受缓存
                    utwUtil.updateState(utw.getUtw_id(), 0);//修改任务进行时状态
                    result += utw.getUtw_result();//获取该进行时完成次数
                    //需要等数据整理完获取数据
                }
                boolean ok = utUtil.updateResult(ut.getUt_allresult() + result, ut.getUt_id());//修改任务接受任务表的总条数
                if (!ok) {
                    LogUtil.warn("数据没有入库成功");
                }
            }
        }
        return 0;
    }

    public Task findTaskbyId(String t_id) {
        return util.findTaskbyId(t_id);
    }

    public TestResult getTaskResult(String tid, AtomicInteger ok) {
        Task t = util.findTaskbyId(tid);

        if (t == null) {
            ok.set(3);
            return null;
        }
        if ("准备中".equals(t.getT_state())) {
            ok.set(8);
            return null;
        }
        TestResult result = new TestResult();
        result.setTid(tid);
        result.setTstate(t.getT_state());

        List<Ut> uts = utUtil.findutbyTid(tid);
        if (uts.size() == 0) {
            ok.set(9);
            return null;
        }
        List<TestDetails> details = new ArrayList<>();
        long cost = 0L;
        long success = 0L;
        for (Ut ut : uts) {
            long s = 0L;
            cost += ut.getUt_allresult();
            TestDetails testDetails = new TestDetails();
            testDetails.setUid(ut.getUt_uid());
            testDetails.setNums((long) ut.getUt_allresult());
            testDetails.setStatu(ut.getUt_state());
            testDetails.setGettime(ut.getUt_time());

            List<Ut_working> ut_workings = utwUtil.findOkTasks(ut.getUt_id());
            testDetails.setCost(ut_workings.size());

            for (Ut_working utw : ut_workings) {
                String name = utw.getUtw_utid() + "_" + utw.getUtw_id() + "_out/part-r-00000";
                hadoopTemplate.getFile(outUrl + name, downUrl);
                Map<String, Integer> cows = fileUtil.readMrFile("part-r-00000");
                fileUtil.rmFile("part-r-00000");
                for (String ss : cows.keySet()) {
                    if (ss.contains("是否成功 true"))
                        s += cows.get(ss);
                    //TODO:之后分析的数据可以更详细
                }
            }
            testDetails.setSuccess((1.0 * s) / (1.0 * ut.getUt_allresult()));
            success += s;
            details.add(testDetails);
        }
        result.setTnum(uts.size());
        result.setTcost(cost);
        result.setSuccess((1.0 * success) / (1.0 * cost));
        result.setMore(details);
        //TODO:之后可以把这些数据存入mysql，不用每次查询都要再算一遍

        return result;
    }
}
