package com.xiaolu.service.impl;

import com.xiaolu.dao.JobMapper;
import com.xiaolu.domain.ScheduleJob;
import com.xiaolu.quartz.springQuartz.ScheduleUtil;
import com.xiaolu.service.JobService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/9.
 */
@Service("jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    JobMapper jobMapper;

    @Autowired
    Scheduler scheduler;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    //获取单条job
    @Override
    public Map selectJobListById(Integer jobId) {
        return jobMapper.selectJobListById(jobId);
    }

    //查询所有job
    @Override
    public List selectJobAll() {
        return jobMapper.selectJobAll();
    }

    //新增job
    @Override
    public int insertJob(ScheduleJob scheduleJob) {
        return jobMapper.insertJob(scheduleJob);
    }

    //立即执行
    @Override
    public void execJob(int jobId) {
        Map map = selectJobListById(jobId);
        ScheduleUtil.run(scheduler,map);
    }

    //暂停
    @Override
    public void pauseJob(int jobId) {
        ScheduleUtil.pauseJob(scheduler,jobId);
    }

    //恢复
    @Override
    public void resumeJob(int jobId) {
        ScheduleUtil.resumeJob(scheduler,jobId);
    }

    //删除
    @Override
    public void deleteScheduleJob(int jobId) {
        ScheduleUtil.deleteScheduleJob(scheduler,jobId);
    }

    //初始化方法,创建所有定时任务
    @PostConstruct
    public void init(){
        //获取所有任务
        List<Map> jobList = selectJobAll();
        for (Map job : jobList){
            Integer jobId = (Integer) job.get("jobId");
            //ScheduleUtil.createScheduleJob(scheduler, job);
            CronTrigger cronTrigger = (CronTrigger) ScheduleUtil.getCronTrigger(scheduler,jobId);
            //如果不存在则创建
            if (cronTrigger == null) {
                ScheduleUtil.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtil.updateScheduleJob(scheduler, job);
            }
        }

    }
}
