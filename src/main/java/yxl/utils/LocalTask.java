package yxl.utils;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import yxl.UserAndTask.entity.Task;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class LocalTask {
    private final ConcurrentHashMap<String,Task> tasks=new ConcurrentHashMap<>();

    public void addLocal(@NonNull Task task){
        tasks.put(task.getT_id(), task);
    }

    public Task getLocal(@NonNull String tid){
        if(!tasks.containsKey(tid))
            return null;
        return tasks.get(tid);
    }

    public void addLocal(@NonNull List<Task> ts){
        for (Task t:ts){
            tasks.put(t.getT_id(),t);
        }
    }
}
