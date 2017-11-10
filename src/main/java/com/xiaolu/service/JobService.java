package com.xiaolu.service;

import com.xiaolu.domain.ScheduleJob;

import java.util.Map;

/**
 * Created by chinaD on 2017/11/9.
 */
public interface JobService {

    Map selectJobListById (Integer jobId);

    int insertJob (ScheduleJob scheduleJob);

}
