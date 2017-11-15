package com.xiaolu.dao;

import com.xiaolu.domain.ScheduleJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/9.
 */
public interface JobMapper {

    Map selectJobListById (@Param("jobId")Integer jobId);

    List selectJobAll ();

    int insertJob (ScheduleJob scheduleJob);
}
