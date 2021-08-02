package yxl.UserAndTask.service;


import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.util.TaskUtil;
import yxl.DataToRedis.util.RedisUtil;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.util.IdsUtil;
import yxl.UserAndTask.util.LocalTable;
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
    private LocalTable localTable;

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
        if (!util.containsTid(tid))
            return 3;
        if(!util.updateTask(tid, state)){
            return -1;
        }
        Task n=util.findTaskbyId(tid);
        if(!redisUtil.setex(n.getT_id(),n,60*60)){
            LogUtil.warn("新建任务没有存入缓存！");
        }
        localTable.addLocal(n);
        return 0;
    }
}
