package yxl.ServerContext.TimerJob;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import yxl.utils.LogUtil;

/**
 * @Author: yxl
 * @Date: 2022/7/25 14:42
 */

@Component
public class ServerJobs {

    @Async("jobThreadPool")
    @Scheduled(cron = "0 10 * * * *")
    public void justDoItA() {
        LogUtil.info(System.currentTimeMillis() + " : Quartz test");
    }
}
