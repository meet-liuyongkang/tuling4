package com.tuling.spring.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/8 5:34 下午
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("asyncServiceExecutor")
    public ThreadPoolTaskExecutor asyncRabbitTimeoutServiceExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(2);
        //核心线程若处于闲置状态的话，超过一定的时间(KeepAliveTime)，就会销毁掉。
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(2);
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(300);
        //加入装饰器
        threadPoolTaskExecutor.setTaskDecorator(new TaskDecoratorTest());
        //配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix("test-log-");
        //拒绝策略:只要线程池未关闭，该策略直接在调用者线程中串行运行被丢弃的任务，显然这样不会真的丢弃任务，但是可能会造成调用者性能急剧下降
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
