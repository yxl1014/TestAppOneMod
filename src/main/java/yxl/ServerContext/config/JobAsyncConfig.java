package yxl.ServerContext.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author: yxl
 * @Date: 2022/7/25 15:11
 */

@Configurable
public class JobAsyncConfig {

    @Bean(name = "jobThreadPool")
    public Executor bankExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数为 3
        executor.setCorePoolSize(2);
        // 最大线程数为10
        executor.setMaxPoolSize(10);
        // 任务队列的大小
        executor.setQueueCapacity(10);
        // 线程前缀名
        executor.setThreadNamePrefix("jobThreadPool-");
        // 线程存活时间
        executor.setKeepAliveSeconds(30);
        // 初始化
        executor.initialize();
        return executor;
    }
}
