package com.tuling.redis.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @descriptionn jedis 连接Redis集群
 * @date 2022/3/15 9:55 上午
 */
public class JedisClusterTest {

    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        hostAndPortSet.add(new HostAndPort("192.168.254.101", 8001));
        hostAndPortSet.add(new HostAndPort("192.168.254.101", 8004));

        hostAndPortSet.add(new HostAndPort("192.168.254.102", 8002));
        hostAndPortSet.add(new HostAndPort("192.168.254.102", 8005));

        hostAndPortSet.add(new HostAndPort("192.168.254.103", 8003));
        hostAndPortSet.add(new HostAndPort("192.168.254.103", 8006));


        JedisCluster jedisCluster = null;
        try {
            /**
             * connectionTimeout 连接上服务的等待时间
             * soTimeout 连接上之后，到返回结果的等待时间
             * maxAttempt 连接异常时，最大重试次数
             */
            jedisCluster = new JedisCluster(hostAndPortSet, 10 * 000, 5 * 1000, 3, "jiangyue", jedisPoolConfig);

            jedisCluster.set("jy003", "003");
            String jy003 = jedisCluster.get("jy003");
            System.out.println(jy003);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedisCluster != null){
                jedisCluster.close();
            }
        }

    }

}
