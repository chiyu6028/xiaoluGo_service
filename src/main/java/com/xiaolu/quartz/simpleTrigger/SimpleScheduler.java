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
            JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("job","jgroup").build();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put("hello","world");

            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5).withRepeatCount(3);

            SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger","tgroup")
                    .startNow().withSchedule(simpleScheduleBuilder).build();


            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,simpleTrigger);
            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
