package com.tuling.nginx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/29 11:19 上午
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ThreadPoolTaskExecutor asyncServiceExecutor;

    @GetMapping("setThreadLocal")
    public void test(@RequestParam String name){
        System.out.println("Nginx服务器收到消息：" + name);
    }
}