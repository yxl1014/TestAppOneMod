package yxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.util.GsonUtil;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
  /*      User user=new User();
        user.setU_tel("11111111111");
        user.setU_name("yyy");
        user.setU_password("123456");
        user.setU_email("1111111111@qq.com");
        System.out.println(GsonUtil.toJson(user));*/
        SpringApplication.run(MainApplication.class,args);

    }
}
