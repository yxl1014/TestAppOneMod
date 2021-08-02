package yxl.UserAndTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yxl.UserAndTask.annotation.LogWeb;
import yxl.UserAndTask.entity.Result;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.service.TaskServiceImpl;
import yxl.UserAndTask.service.UtServiceImpl;
import yxl.UserAndTask.util.ErrorSwitch;
import yxl.UserAndTask.util.GsonUtil;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private UtServiceImpl utService;


    @PostMapping("/init")
    @ResponseBody
    @LogWeb(url = "/tasks/init", op = "数据初始化", type = "任务操作")
    public String init(){
        List<Task> tasks = taskService.getAll();
        return GsonUtil.toJson(new Result(GsonUtil.toJson(tasks), null));
    }

    @PostMapping("/prod/makeTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/makeTask",op = "发布任务",type = "任务操作")
    public String maketask(@RequestBody Task task){
        Task t=taskService.maketask(task);
        return GsonUtil.toJson(new Result(GsonUtil.toJson(t),null));
    }


    @PostMapping("/prod/startTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/startTask",op = "生产者开始任务",type = "任务操作")
    public String startTask(@RequestBody String tid){
        int ok=taskService.updateState(tid,"开始");
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok),null));
    }

    @PostMapping("/prod/stopTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/stopTask",op = "生产者暂停任务",type = "任务操作")
    public String stopTask(@RequestBody String tid){
        int ok=taskService.updateState(tid,"暂停");
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok),null));
    }

    @PostMapping("/prod/endTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/endTask",op = "生产者取消任务",type = "任务操作")
    public String endTask(@RequestBody String tid){
        int ok=taskService.updateState(tid,"已取消");
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok),null));
    }


    @PostMapping("/cons/getTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/getTask",op = "接受任务",type = "任务操作")
    public String cgetTask(@RequestBody String tid){
        Integer ok= -1;
        Task t=utService.getTask(tid,ok);
        return null;
    }


    @PostMapping("/cons/startTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/startTask",op = "消费者开始任务",type = "任务操作")
    public String cstartTask(@RequestBody String tid){
        return null;
    }

    @PostMapping("/cons/stopTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/stopTask",op = "消费者结束任务",type = "任务操作")
    public String cstopTask(@RequestBody String tid){
        return null;
    }
}
