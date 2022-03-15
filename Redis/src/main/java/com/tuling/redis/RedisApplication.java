package com.tuling.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/3/10 7:39 下午
 */
@SpringBootApplication
public class RedisApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RedisApplication.class, args);
    }
}
