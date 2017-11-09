package com.xiaolu.service.impl;

import com.xiaolu.dao.JobMapper;
import com.xiaolu.domain.ScheduleJob;
import com.xiaolu.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chinaD on 2017/11/9.
 */
@Service("jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    JobMapper jobMapper;

    @Override
    public List selectJobList(int jobId) {
        return jobMapper.selectJobList(jobId);
    }

    @Override
    public int insertJob(ScheduleJob scheduleJob) {
        return jobMapper.insertJob(scheduleJob);
    }
}
