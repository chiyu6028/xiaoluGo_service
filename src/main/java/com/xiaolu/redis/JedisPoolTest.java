package com.xiaolu.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chinaD on 2017/9/14.
 */
public class JedisPoolTest {

    public static void main(String[] args) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        Set sentinelSet = new HashSet<String>();
        sentinelSet.add("119.29.107.133:26379");
        sentinelSet.add("119.29.107.133:26380");
        sentinelSet.add("119.29.107.133:26381");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",sentinelSet,
                poolConfig,4000,"kdbase");

        Jedis jedis = null;

        try {
            jedis = jedisSentinelPool.getResource();
            //jedis.set("hello","aaa");
            //jedis.rpush("listKey","a","b","c","d");
            //jedis.linsert("listKey", BinaryClient.LIST_POSITION.AFTER,"b","java");
            //System.out.println(jedis.lrange("listKey",0,-1));
            //jedis.del("listKey");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

    }
}
