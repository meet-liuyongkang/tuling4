package com.tuling.spring.delayed;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     rabbitmq的延时队列插件，实现延时队列
 * @date 2022/4/27 10:25 上午
 */
@Configuration
public class DelayedSpringConfig {

    /**
     * 交换机
     */
    public static final String DELAYED_SPRING_EXCHANGE = "DELAYED_SPRING_EXCHANGE";

    /**
     * 队列
     */
    public static final String DELAYED_SPRING_QUEUE = "DELAYED_SPRING_QUEUE";

    /**
     * routingKey
     */
    public static final String DELAYED_SPRING_ROUTING_KEY = "delayed";

    /**
     * 声明自定义交换机
     * @return
     */
    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> params = new HashMap<>(1);
        params.put("x-delayed-type", ExchangeTypes.DIRECT);
        return new CustomExchange(DELAYED_SPRING_EXCHANGE, "x-delayed-message", false, true, params);
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_SPRING_QUEUE, false, false, true);
    }

    /**
     * 自定义交换机和队列绑定
     * @return
     */
    @Bean
    public Binding delayedBinding(Queue delayedQueue, CustomExchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_SPRING_ROUTING_KEY).noargs();
    }

}
