package yxl.ServerContext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import yxl.dto.ServerProto;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yxl
 * @Date: 2022/7/25 10:48
 */

@Configuration
public class CollectionBeansConfig {

    @Bean(name = "taskTimerMap")
    public HashMap<String, ServerProto.TaskTimer> taskTimerMap() {
        return new HashMap<String, ServerProto.TaskTimer>();
    }

    @Bean(name = "userMap")
    public HashMap<String, ServerProto.UserIP> userMap() {
        return new HashMap<String, ServerProto.UserIP>();
    }
}
