package com.xiaolu.quartz.simpleTrigger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by chinaD on 2017/11/1.
 */
public class SimpleJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(jobExecutionContext.getTrigger()+"trigger time : "+ new Date());
    }
}