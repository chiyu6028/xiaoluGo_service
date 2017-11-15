package com.xiaolu;

import com.xiaolu.domain.ScheduleJob;
import com.xiaolu.domain.User;
import com.xiaolu.interceptor.ValidateInterceptor;
import com.xiaolu.quartz.task.JobTask;
import com.xiaolu.service.JobService;
import com.xiaolu.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by chinaD on 2017/9/19.
 */
public class JobTest {

    private final Logger logger = LoggerFactory.getLogger(JobTest.class);

    private JobService jobService;

    private JobTask jobTask;

    @Before
    public void before() {
        //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
        ApplicationContext cx = new ClassPathXmlApplicationContext(new String[]{"spring.xml", "spring_mybatis.xml"});
        //从Spring容器中根据bean的id取出我们要使用的userService对象
        jobService = (JobService) cx.getBean("jobService");
        jobTask = (JobTask) cx.getBean("jobTask");

    }

    @Test
    public void testJobTask() {
        jobTask.task();
    }

    @Test
    public void testJobSelect() {
        Map jobList = jobService.selectJobListById(Integer.valueOf(1));
        logger.debug(jobList.toString());
        System.out.println(jobList.toString());
    }

    @Test
    public void testJobSelectAll() {
        List jobList = jobService.selectJobAll();
        logger.debug(jobList.toString());
        System.out.println(jobList.toString());
    }

    @Test
    public void testJobInsert() {
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setBeanClass("com.xiaolu.quartz.task.JobTask");
        scheduleJob.setCronExpression("0/5 * * * * ?");
        scheduleJob.setDescription("每五秒执行一次");
        scheduleJob.setIsConcurrent("0");
        scheduleJob.setJobGroup("group");
        scheduleJob.setJobName("第二个任务");
        scheduleJob.setMethodName("task");
        scheduleJob.setJobStatus("0");
        scheduleJob.setSpringId("jobTask");
        jobService.insertJob(scheduleJob);
    }
}
