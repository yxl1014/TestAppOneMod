package yxl.ServerContext.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import yxl.dto.ServerProto;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yxl
 * @Date: 2022/7/25 10:48
 */

@Configurable
public class CollectionBeansConfig {

    @Bean(name = "TaskTimerMap")
    public Map<String, ServerProto.TaskTimer> getTaskTimerMap() {
        return new HashMap<>();
    }
}
