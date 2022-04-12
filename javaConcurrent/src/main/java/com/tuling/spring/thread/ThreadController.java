package com.tuling.spring.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/8 5:30 下午
 */

@RestController
@RequestMapping("/test")
public class ThreadController {

    @Autowired
    private ThreadPoolTaskExecutor asyncServiceExecutor;

    @GetMapping("setThreadLocal")
    public void test(@RequestParam String name){
        System.out.println("线程名称："+ Thread.currentThread().getName() +"，主线程值：" + name);
        MetaUserContext.setUserName(name);

        asyncServiceExecutor.execute(new Runnable() {
            @Override
            public void run() {
                testA();
            }
        });
    }

    public void testA(){
        System.out.println("线程名称：" +Thread.currentThread().getName() +", 获取到的值为：" + MetaUserContext.getUserName());
    }

}
