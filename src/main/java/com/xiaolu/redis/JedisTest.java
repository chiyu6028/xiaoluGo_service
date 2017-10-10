package com.xiaolu.redis;


import com.xiaolu.util.RedisUtil;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by chinaD on 2017/9/13.
 */
public class JedisTest {



    public static void main(String[] args) {
        Jedis jedis = null;
        String a = "a";
        String b = "a";

        System.out.println(a==b);

        try{
            jedis = new Jedis("119.29.107.133",6380);
            jedis.auth("kdbase");
            jedis.set("hello","world");
            System.out.println(jedis.get("hello"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

    }
}
