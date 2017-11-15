package com.xiaolu.service;

import com.xiaolu.domain.ScheduleJob;

import java.util.List;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/9.
 */
public interface JobService {

    Map selectJobListById (Integer jobId);

    List selectJobAll ();

    int insertJob (ScheduleJob scheduleJob);

    void execJob (int jobId);

    void pauseJob(int jobId);

    void resumeJob(int jobId);

    void deleteScheduleJob(int jobId);

}
