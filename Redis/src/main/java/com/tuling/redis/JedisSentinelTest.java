package com.tuling.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/3/10 6:04 下午
 */
public class JedisSentinelTest {

    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        Set<String> sentinelHostAndPort = new HashSet<String>();
        sentinelHostAndPort.add(new HostAndPort("192.168.254.104", 26379).toString());
        sentinelHostAndPort.add(new HostAndPort("192.168.254.104", 26380).toString());
        sentinelHostAndPort.add(new HostAndPort("192.168.254.104", 26381).toString());

        String masterName = "mymaster";
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinelHostAndPort, jedisPoolConfig, 3000, null);
        Jedis jedis = null;

        try {
            jedis = jedisSentinelPool.getResource();
            jedis.set("sentinel_001", "001");
            String sentinel_001 = jedis.get("sentinel_001");
            System.out.println(sentinel_001);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 注意，这里的close不是关闭连接，而是将连接归还到连接池中。
            if(jedis != null){
                jedis.close();
            }
        }


    }

}
