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
        SpringApplication.run(MainApplication.class,args);
    }
}
