package com.xiaolu.quartz.springQuartz;

import com.xiaolu.quartz.task.JobTask;
import com.xiaolu.util.Constant;
import com.xiaolu.util.SpringContextTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * jobDetail
 * Created by chinaD on 2017/11/13.
 */
public class ScheduleJob extends QuartzJobBean {

    private final Log logger = LogFactory.getLog(getClass());
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //获取map
        Map jobDataMap = (Map) jobExecutionContext.getMergedJobDataMap().get(Constant.SCHEDULE_JOB_ID);
        System.out.println(jobDataMap.toString());

        //获取job内容
        String springId = String.valueOf(jobDataMap.get("springId"));
        String methodName = String.valueOf(jobDataMap.get("methodName"));
        String params = String.valueOf(jobDataMap.get("params"));
        String beanClass = String.valueOf(jobDataMap.get("beanClass"));

        if (beanClass != null && !"".equals(beanClass)) {
            try {
                Class bean = Class.forName(beanClass);
                Method method = null;
                if (params.isEmpty()){
                    method = bean.getDeclaredMethod(methodName);
                    method.invoke(bean.newInstance());
                }else {
                    method = bean.getDeclaredMethod(methodName,String.class);
                    method.invoke(bean.newInstance(),params);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class bean = SpringContextTool.getBean(springId).getClass();
                Method method = null;
                if (params != null && !"".equals(params)){
                    method = bean.getDeclaredMethod(methodName,String.class);
                    method.invoke(bean.newInstance(),params);
                }else {
                    method = bean.getDeclaredMethod(methodName);
                    method.invoke(bean.newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
