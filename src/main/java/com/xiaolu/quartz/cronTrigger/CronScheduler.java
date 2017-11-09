package com.xiaolu.quartz.cronTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by chinaD on 2017/11/8.
 */
public class CronScheduler {

    public static void main(String[] args) {

        try {
            //1.创建jobDetail
            JobDetail jobDetail = JobBuilder.newJob(CronJob.class).withIdentity("job2","jgroup2").build();

            //2.创建CronTrigger
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger","tgroup")
                    .startNow().withSchedule(cronScheduleBuilder).build();

            //3.创建schedule
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,cronTrigger);

            //4.启动
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
