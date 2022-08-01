package yxl.UserAndTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yxl.UserAndTask.annotation.LogWeb;
import yxl.UserAndTask.annotation.NoToken;
import yxl.UserAndTask.entity.Cards;
import yxl.UserAndTask.entity.Producer;
import yxl.UserAndTask.entity.Result;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.service.UserServiceImpl;
import yxl.utils.ErrorSwitch;
import yxl.utils.GsonUtil;
import yxl.utils.JWTUtil;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    @ResponseBody
    @NoToken
    @LogWeb(url = "/users/login", op = "用户登录", type = "用户操作")
    public String login(@RequestBody User user) {
        User u = userService.login(user);
        return GsonUtil.toJson(new Result(GsonUtil.toJson(u), JWTUtil.sign(u, 3 * 24 * 60 * 60 * 1000)));//有效期三天
    }

/*
    @PostMapping("/test")
    @ResponseBody
    @NoToken
    @LogWeb(url = "/users/test", op = "用户登录", type = "用户操作")
    public String test() {
        return "isok";//有效期三天
    }
*/

    @PostMapping("/logon")
    @ResponseBody
    @NoToken
    @LogWeb(url = "/users/logon", op = "用户注册", type = "用户操作")
    public String logon(@RequestBody User user) {
        int ok = userService.logon(user);
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }

    @PostMapping("/update")
    @ResponseBody
    @LogWeb(url = "/users/update", op = "修改用户信息", type = "用户操作")
    public String update(/*@RequestBody User old,*/ @RequestBody User user) {
        int ok = userService.update(user);
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }

    @PostMapping("/uplevel")
    @ResponseBody
    @LogWeb(url = "/users/uplevel", op = "成为提交者", type = "用户操作")
    public String uplevel(@RequestBody Producer producer) {
        boolean ok = userService.uplevel(producer);
        return GsonUtil.toJson(new Result(String.valueOf(ok), null));
    }

    @PostMapping("/tovip")
    @ResponseBody
    @LogWeb(url = "/users/tovip", op = "充值VIP", type = "用户操作")
    public String tovip() {
        boolean ok = userService.tovip();
        return GsonUtil.toJson(new Result(String.valueOf(ok), null));
    }

    @PostMapping(value = "/payin", consumes = "application/json")
    @ResponseBody
    @LogWeb(url = "/users/payin", op = "用户充值", type = "用户操作")
    public String payin(@RequestBody Cards cards) {
        float value = Float.parseFloat(cards.getCost());
        boolean ok = userService.payIn(value);
        return GsonUtil.toJson(new Result(String.valueOf(ok), null));
    }

    @PostMapping("/payout")
    @ResponseBody
    @LogWeb(url = "/users/payout", op = "用户提现", type = "用户操作")
    public String payout(@RequestBody Cards cards) {
        float value = Float.parseFloat(cards.getCost());
        int ok = userService.payOut(value, cards.getCardid());
        return GsonUtil.toJson(new Result(ErrorSwitch.getValue(ok), null));
    }

}
