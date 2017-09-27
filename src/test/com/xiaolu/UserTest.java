package com.xiaolu;

import com.xiaolu.domain.User;
import com.xiaolu.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * Created by chinaD on 2017/9/19.
 */
public class UserTest {

    private UserService userService;

    @Before
    public void before(){
        //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
        ApplicationContext cx = new ClassPathXmlApplicationContext(new String[] {"spring.xml","spring_mybatis.xml"});
        //从Spring容器中根据bean的id取出我们要使用的userService对象
        userService = (UserService) cx.getBean("userService");
    }

    @Test
    public void testSelectByPrimaryKey(){
        //User user = new User();
        User user = userService.selectByPrimaryKey("1");
        System.out.println(user.getUser_name());
    }

    @Test
    public void testSelectUserBypassword(){
        //User user = new User();
        User user = userService.selectUserBypassword("1","123456");
        System.out.println(user.getUser_name());
    }
}
