package com.tuling.redis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/3/10 8:14 下午
 */
@RestController
@RequestMapping("test")
public class IndexController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/testGet")
    public String testGet(@RequestParam String redisKey){
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    @GetMapping("/testSet")
    public void testSet(@RequestParam String redisKey, String redisValue){
        stringRedisTemplate.opsForValue().set(redisKey, redisValue);
    }
}
