package yxl.UserAndTask.service;


import lombok.NonNull;
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
import yxl.UserAndTask.util.IdsUtil;
import yxl.UserAndTask.util.LocalTask;
import yxl.UserAndTask.util.LogUtil;
import yxl.UserAndTask.util.TlUserUtil;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    public List<Task> getAll(){
        List<Task> tasks=util.findTasks();
        localTable.addLocal(tasks);
        for(Task t:tasks){
            redisUtil.setex(t.getT_name(),t, 60 * 60);
        }
        return tasks;
    }

    public Task maketask(@NonNull Task task) {
        User user = TlUserUtil.getThreadLocal();
        task.setT_id(IdsUtil.getTid(task.getT_type(), user.getU_id().substring(15)));
        task.setT_uid(user.getU_id());
        task.setT_cost(0);
        task.setT_state("准备中");
        task.setT_stime(new Timestamp(new Date().getTime()));
        if(!util.insertTask(task)){
            LogUtil.warn("任务入库失败重新录入！");
            return null;
        }
        if(!redisUtil.setex(task.getT_id(),task,60*60)){
            LogUtil.warn("新建任务没有存入缓存！");
        }
        localTable.addLocal(task);
        return task;
    }

    public int updateState(@NonNull String tid, String state) {//3：没有该任务
        if (!util.containsTid(tid))//查询这个任务是否存在
            return 3;
        if(!util.updateTask(tid, state)){//修改任务状态
            return -1;
        }
        Task n=util.findTaskbyId(tid);//得到这个任务
        if(!redisUtil.setex(n.getT_id(),n,60*60)){//将任务存入redis
            LogUtil.warn("新建任务没有存入缓存！");
        }
        localTable.addLocal(n);//将任务存入本地内存
        if(!"开始".equals(state)){//判断状态类型，若不是开始,则需要停止所有测试中任务
            List<Ut> uts=utUtil.findutbyTid(tid);//找到所有接受这个任务的用户
            for (Ut ut:uts){//遍历
                List<Ut_working> utws=utwUtil.findNookTasks(ut.getUt_id());//查找所有还没有执行完任务
                if(utws.size()==0)
                    continue;
                data.pushData();//清空数据接受缓存
                int result=0;//请求成功总条数
                for (Ut_working utw:utws){//遍历
                    utwUtil.updateState(utw.getUtw_id(),0);//修改任务进行时状态
                    result+=utw.getUtw_result();//获取该进行时完成次数
                    //需要等数据整理完获取数据
                }
                boolean ok=utUtil.updateResult(ut.getUt_allresult()+result,ut.getUt_id());//修改任务接受任务表的总条数
                if(!ok){
                    LogUtil.warn("数据没有入库成功");
                }
            }
        }
        return 0;
    }
}
