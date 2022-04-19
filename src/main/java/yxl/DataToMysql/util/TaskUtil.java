package yxl.DataToMysql.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.read.SelectTask;
import yxl.DataToMysql.write.UpdateTask;
import yxl.UserAndTask.entity.Task;


import java.util.List;

@Service
public class TaskUtil {
    @Autowired
    private SelectTask select;
    @Autowired
    private UpdateTask update;

    public boolean containsTid(String tid){
        return select.containsTid(tid);
    }

    public Task findTaskbyId(String tid){
        return select.findTaskbyId(tid);
    }

    public List<Task> findTasks(){
        return select.findTasks();
    }

    public List<Task> findTasksbyUid(String uid){
        return select.findTasksbyUid(uid);
    }

    public boolean insertTask(Task t){
        return update.insertTask(t);
    }

    public boolean updateTask(String tid,String state){
        return update.updateTask(tid, state);
    }
}
