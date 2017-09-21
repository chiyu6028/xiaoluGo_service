package com.xiaolu;

import com.xiaolu.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chinaD on 2017/9/21.
 */
public class RedisTest {

    @Autowired
    Redisson redission;

    @Before
    public void before(){
        //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
        ApplicationContext cx = new ClassPathXmlApplicationContext(new String[] {"spring_redis.xml"});
        //从Spring容器中根据bean的id取出我们要使用的userService对象
        cx.getApplicationName();
        redission = (Redisson) cx.getBean("myRedisson");
    }

    @Test
    public void testRedission(){
        System.out.println(redission);
        RBucket<String> bucket = redission.getBucket("hello");
        System.out.println(bucket.get());
    }
}
