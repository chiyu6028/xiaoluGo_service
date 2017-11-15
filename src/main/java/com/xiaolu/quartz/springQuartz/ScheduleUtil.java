package com.xiaolu.quartz.springQuartz;

import com.xiaolu.util.Constant;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;

/**
 * Created by chinaD on 2017/11/9.
 */
public class ScheduleUtil {

    /**
     * 获取触发器key
     * @param jobId
     * @return
     */
    public static TriggerKey getTriggerKey(int jobId) {
        return TriggerKey.triggerKey(String.valueOf(jobId));
    }

    /**
     * 获取jobKey
     * @param jobId
     * @return
     */
    public static JobKey getJobKey(int jobId) {
        return JobKey.jobKey(String.valueOf(jobId));
    }

    /**
     * 获取表达式触发器
     * @param scheduler
     * @param jobId
     * @return
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, int jobId) {
        try {
            TriggerKey triggerKey = getTriggerKey(jobId);
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     * @param scheduler
     * @param jobMap
     */
    public static void createScheduleJob(Scheduler scheduler, Map jobMap) {
        try {
            int jobId = Integer.parseInt(String.valueOf(jobMap.get("jobId")));
            String cron = String.valueOf(jobMap.get("cronExpression"));
            int status = Integer.parseInt(String.valueOf(jobMap.get("jobStatus")));
            String jobName = String.valueOf(jobMap.get("jobName"));
            String jobGroup = String.valueOf(jobMap.get("jobGroup"));
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(jobId)).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(Constant.SCHEDULE_JOB_ID, jobMap);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId)).withSchedule(scheduleBuilder).build();

            //scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.scheduleJob(jobDetail, trigger);

            //scheduler.start();

            //暂停任务
            if (status == Constant.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, jobId);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, Map jobMap) {
        try {
            int jobId = Integer.parseInt(String.valueOf(jobMap.get("jobId")));
            String cron = String.valueOf(jobMap.get("cronExpression"));
            int status = Integer.parseInt(String.valueOf(jobMap.get("jobStatus")));

            TriggerKey triggerKey = getTriggerKey(jobId);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, jobId);

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(Constant.SCHEDULE_JOB_ID, jobMap);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (status == Constant.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, jobId);
            }

        } catch (SchedulerException e) {
            throw new RuntimeException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, Map scheduleJob) {
        try {
            Integer jobId = Integer.parseInt(String.valueOf(scheduleJob.get("jobId")));
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(Constant.SCHEDULE_JOB_ID, scheduleJob);

            scheduler.triggerJob(getJobKey(jobId), dataMap);
        } catch (Exception e) {
            throw new RuntimeException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, int jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, int jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, int jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("删除定时任务失败", e);
        }
    }
}
