package com.xiaolu;

import com.xiaolu.activeMQ.spring.QueueSender;
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
public class JmsTest {

    private final Logger logger = LoggerFactory.getLogger(JmsTest.class);

    private QueueSender queueSender;

    @Before
    public void before() {
        //使用"spring.xml"和"spring-spring_activeMQ.xml"这两个配置文件创建Spring上下文
        ApplicationContext cx = new ClassPathXmlApplicationContext(new String[]{"spring.xml", "spring_mybatis.xml", "spring_activeMQ.xml"});
        //从Spring容器中根据bean的id取出我们要使用的userService对象
        queueSender = (QueueSender) cx.getBean("queueSender");

    }

    @Test
    public void testSend() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queueSender.send("this is queueSender: "+i);
            Thread.sleep(1000);
        }
    }

    @Test
    public void testSendTwo() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queueSender.sendTwo("this is queueSender Two: "+i);
            Thread.sleep(1000);
        }
    }

}
