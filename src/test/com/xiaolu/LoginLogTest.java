package com.xiaolu;

import com.xiaolu.service.LoginLogService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/7.
 */
public class LoginLogTest {

    private LoginLogService loginLogService;

    @Before
    public void before(){
        //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
        ApplicationContext cx = new ClassPathXmlApplicationContext(new String[] {"spring.xml","spring_mybatis.xml"});
        //从Spring容器中根据bean的id取出我们要使用的userService对象
        loginLogService = (LoginLogService) cx.getBean("loginLogService");
    }

    @Test
    public void testLoignLog(){
        Map map = new HashMap();
        map.put("user_id","1");
        map.put("login_ip","127.0.0.1");
        int i = loginLogService.insertLoginLog(map);
    }
}
