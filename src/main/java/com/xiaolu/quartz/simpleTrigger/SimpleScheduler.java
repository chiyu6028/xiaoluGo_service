package com.xiaolu.quartz.simpleTrigger;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

/**
 * Created by chinaD on 2017/11/1.
 */
public class SimpleScheduler {

    public static void main(String[] args) {
        try {
            //第一步，创建dobDetail
            JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("job","jgroup").build();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put("hello","world");

            //第二步，创建触发器
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5).withRepeatCount(3);

            SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger","tgroup")
                    .startNow().withSchedule(simpleScheduleBuilder).build();

            //第三步，创建schedule
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,simpleTrigger);

            //第四步，启动
            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
