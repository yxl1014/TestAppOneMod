package yxl.ServerContext.config.Quartz.config;

import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import yxl.ServerContext.config.Quartz.Job.QuartzTestJob;

/**
 * @Author: yxl
 * @Date: 2022/7/25 14:59
 * <p>
 * 版权声明：本文为CSDN博主「一猿小讲」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/javaforwork/article/details/122916823
 */

@Configuration
public class QuartzTestConfig {

    @Bean
    public JobDetail jobDetail() {
        //指定任务描述具体的实现类
        return JobBuilder.newJob(QuartzTestJob.class)
                // 指定任务的名称
                .withIdentity("QuartzTestJob")
                // 任务描述
                .withDescription("任务描述：Quartz Test")
                // 每次任务执行后进行存储
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger trigger() {
        //创建触发器
        return TriggerBuilder.newTrigger()
                // 绑定工作任务
                .forJob(jobDetail())
                // 每隔 5 秒执行一次 job
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .build();
    }

}
