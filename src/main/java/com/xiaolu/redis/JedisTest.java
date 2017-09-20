package com.xiaolu.redis;


import redis.clients.jedis.Jedis;

/**
 * Created by chinaD on 2017/9/13.
 */
public class JedisTest {


    public static void main(String[] args) {
        Jedis jedis = null;

        try{
            jedis = new Jedis("119.29.107.133",6379);
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
