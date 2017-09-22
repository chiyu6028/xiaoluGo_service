package com.xiaolu.redis;


import com.xiaolu.util.RedisUtil;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * Created by chinaD on 2017/9/13.
 */
public class JedisTest {



    public static void main(String[] args) {
        /*Jedis jedis = null;

        try{
            jedis = new Jedis("119.29.107.133",6380);
            jedis.auth("kdbase");
            jedis.set("hello","world777777777777");
            System.out.println(jedis.get("hello"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }*/

        ApplicationContext cx = new ClassPathXmlApplicationContext(new String[] {"spring_redis.xml"});
        //从Spring容器中根据bean的id取出我们要使用的userService对象
        RedissonClient redission = (Redisson) cx.getBean("redisson");

        RedisUtil redisUtil = new RedisUtil();
        RBucket bucket = redisUtil.getRBucket(redission,"hello");
        System.out.println(bucket.get());

    }
}
