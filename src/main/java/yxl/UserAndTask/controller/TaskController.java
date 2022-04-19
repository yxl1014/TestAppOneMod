package yxl.UserAndTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yxl.UserAndTask.annotation.LogWeb;
import yxl.UserAndTask.entity.Result;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.Ut;
import yxl.UserAndTask.entity.Ut_working;
import yxl.UserAndTask.service.TaskServiceImpl;
import yxl.UserAndTask.service.UtServiceImpl;
import yxl.UserAndTask.util.ErrorSwitch;
import yxl.UserAndTask.util.GsonUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    public String init() {
        List<Task> tasks = taskService.getAll();
        return GsonUtil.toJson(new Result(GsonUtil.toJson(tasks), null));
    }

    @PostMapping("find_task")
    @ResponseBody
    @LogWeb(url = "/tasks/find_task", op = "查询任务", type = "任务操作")
    public String find_task(@RequestBody Task task) {
        Task t = taskService.findTaskbyId(task.getT_id());
        return GsonUtil.toJson(new Result(GsonUtil.toJson(t), null));
    }





    @PostMapping("/cons/makeTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/makeTask", op = "发布任务", type = "任务操作")
    public String maketask(@RequestBody Task task) {
        Task t = taskService.maketask(task);
        return GsonUtil.toJson(new Result(GsonUtil.toJson(t), null));
    }


    @PostMapping("/cons/startTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/startTask", op = "生产者开始任务", type = "任务操作")
    public String startTask(@RequestBody Task task) {
        String tid = task.getT_id();

        int ok = taskService.updateState(tid, "开始");
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }

    @PostMapping("/cons/stopTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/stopTask", op = "生产者暂停任务", type = "任务操作")
    public String stopTask(@RequestBody Task task) {
        String tid = task.getT_id();
        int ok = taskService.updateState(tid, "暂停");
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }

    @PostMapping("/cons/endTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/endTask", op = "生产者取消任务", type = "任务操作")
    public String endTask(@RequestBody Task task) {
        String tid = task.getT_id();
        int ok = taskService.updateState(tid, "已取消");
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }

    @PostMapping("/cons/getMyTask")
    @ResponseBody
    @LogWeb(url = "/tasks/cons/getMyTask", op = "生产者获取所有发布的任务", type = "任务操作")
    public String getUts() {
        List<Task> tasks = taskService.getAllbyUid();
        return GsonUtil.toJson(new Result(GsonUtil.toJson(tasks), null));
    }

    @PostMapping("/prod/find_Uts")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/find_Uts", op = "消费者获取所有接受的任务", type = "任务操作")
    public String find_Uts(){
        //获取用户接受的所有任务
        List<Task> tasks=utService.getcons_Tasks();
        return GsonUtil.toJson(new Result(GsonUtil.toJson(tasks),null));
    }


    @PostMapping("/prod/getTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/getTask", op = "接受任务", type = "任务操作")
    public String pgetTask(@RequestBody Task task) {
        String tid = task.getT_id();
        AtomicInteger ok = new AtomicInteger(-1);
        Task t = utService.getTask(tid, ok);
        return GsonUtil.toJson(new Result(ok.get() == 0 ? GsonUtil.toJson(t) : ErrorSwitch.getValue(ok.get()), null));
    }


    @PostMapping("/prod/startTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/startTask", op = "消费者开始任务", type = "任务操作")
    public String pstartTask(@RequestBody Task task) {
        String tid = task.getT_id();
        AtomicInteger ok = new AtomicInteger(-1);
        Ut_working utw = utService.startTask(tid,ok);
        return GsonUtil.toJson(new Result(ok.get() == 0 ? GsonUtil.toJson(utw) : ErrorSwitch.getValue(ok.get()), null));
    }

    @PostMapping("/prod/stopTask")
    @ResponseBody
    @LogWeb(url = "/tasks/prod/stopTask", op = "消费者结束任务", type = "任务操作")
    public String pstopTask(@RequestBody Task task) {
        String tid = task.getT_id();
        int ok = utService.stopTask(tid);
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }
}
