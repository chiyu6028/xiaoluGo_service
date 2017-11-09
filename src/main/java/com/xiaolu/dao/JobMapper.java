package com.xiaolu.dao;

import com.xiaolu.domain.ScheduleJob;

import java.util.List;

/**
 * Created by chinaD on 2017/11/9.
 */
public interface JobMapper {

    List selectJobList (int jobId);

    int insertJob (ScheduleJob scheduleJob);
}
