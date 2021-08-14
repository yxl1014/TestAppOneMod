package yxl;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yxl.UserAndTask.entity.Producer;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.util.GsonUtil;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
/*  *//*      User user=new User();
        user.setU_tel("11111111111");
        user.setU_name("yyy");
        user.setU_password("123456");
        user.setU_email("1111111111@qq.com");
        System.out.println(GsonUtil.toJson(user));*//*
        Producer p=new Producer();
        p.setP_address("xxxxxx");
        p.setP_company("xxxxxx");
        System.out.println(GsonUtil.toJson(p));*/
        Task t=new Task();
        t.setT_name("xxx");
        t.setT_type("001");
        t.setT_serverip("127.0.0.1");
        t.setT_serverport("9999");
        t.setT_cost(100);
        t.setT_protocol("http");
        t.setT_context("xxxxxx");
        t.setT_target("yyyyyy");
        System.out.println(GsonUtil.toJson(t));
        SpringApplication.run(MainApplication.class,args);

    }
}
