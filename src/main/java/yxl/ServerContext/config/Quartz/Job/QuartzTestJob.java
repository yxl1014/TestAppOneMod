package yxl.ServerContext.config.Quartz.Job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import yxl.utils.LogUtil;

/**
 * @Author: yxl
 * @Date: 2022/7/25 15:00
 */
public class QuartzTestJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LogUtil.info(System.currentTimeMillis() + " : Quartz test");
    }
}
