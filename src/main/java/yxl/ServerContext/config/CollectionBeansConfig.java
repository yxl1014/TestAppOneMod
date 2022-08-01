package yxl.ServerContext.config;

import org.apache.hadoop.util.hash.Hash;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import yxl.dto.ServerProto;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yxl
 * @Date: 2022/7/25 10:48
 */

@Configurable
public class CollectionBeansConfig {

    @Bean(name = "taskTimerMap")
    public HashMap<String, ServerProto.TaskTimer> taskTimerMap() {
        return new HashMap<>();
    }

    @Bean(name = "userMap")
    public HashMap<String, ServerProto.UserIP> userMap() {
        return new HashMap<>();
    }
}
