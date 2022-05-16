package yxl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import yxl.DataHandle.data.DirData;
import yxl.UserAndTask.entity.Producer;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.util.GsonUtil;

import java.io.File;

@SpringBootApplication
@EnableAsync
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
