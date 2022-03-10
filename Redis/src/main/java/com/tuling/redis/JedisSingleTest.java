package com.tuling.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/3/10 2:49 下午
 */
public class JedisSingleTest {

    public static void main(String[] args) {
        //构建连接池的配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        //构建连接池对象
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.254.104", 6379, 3000, null);

        //从连接池中拿出一个连接对象
        Jedis jedis = null;


        try {
            jedis = jedisPool.getResource();

            jedis.set("jedis001", "001");
            String jedis001 = jedis.get("jedis001");
            System.out.println(jedis001);


            //管道命令的方式：:cat redis.txt | redis‐cli ‐h 127.0.0.1 ‐a password ‐ p 6379 ‐‐pipe
            // 一次请求，发送多条命令，Redis接收到这次请求后，将管道中的命令依次执行。
            //注意：管道命令的方式不支持原子性，任何一条命令执行失败后，仍会继续执行后面的命令。
            jedis.set("pipelined_key", "pipelined");
            Pipeline pipelined = jedis.pipelined();
            for (int i = 0; i < 10; i++) {
                //自增一个string类型的key，模拟报错
                pipelined.incr("pipelined_key");
                pipelined.set("jy" + i,  i + "");
            }
            List<Object> objects = pipelined.syncAndReturnAll();
            for (Object obj : objects){
                System.out.println(obj);
            }


            // TODO 注意，不要在lua脚本中出现死循环或者耗时的操作，因为lua脚本执行过程中，会阻塞其他请求
            //lua脚本：lua脚本就是一个脚本语言，将批量的Redis命令放在一个lua脚本文件中，即可批量执行。
            //特性：它支持原子性，如果有一条报错，则会回滚。也就是要么全部成功，要么全部失败。
            //lua脚本命令执行方式:redis‐cli ‐‐eval /tmp/test.lua , 10
            //KEYS[1] 表示第一个key
            //ARGV[1] 表示第一个value

            //初始化商品10016的库存
            jedis.set("product_count_10016", "15");
            String script = " local count = redis.call('get', KEYS[1]) " +
                    " local a = tonumber(count) " +
                    " local b = tonumber(ARGV[1]) " +
                    " if a >= b then " +
                    " redis.call('set', KEYS[1], a-b) " +
//                    "  这里写一段中文，让它报错，数据会被回滚" +
                    " return 1 " +
                    " end " +
                    " return 0 ";
            List<String> keyList = Arrays.asList("product_count_10016");
            List<String> valueList = Arrays.asList("10");
            Object eval = jedis.eval(script, keyList, valueList);
            System.out.println(eval);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //这里的close不是关闭连接，而是将连接还给连接池中。
            if(jedis != null){
                jedis.close();
            }
        }


    }

}
