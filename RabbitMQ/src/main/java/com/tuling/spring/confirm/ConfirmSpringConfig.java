package com.tuling.spring.confirm;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     发布确认 and 消息回退
 * @date 2022/4/27 3:01 下午
 */
@Configuration
public class ConfirmSpringConfig {

    /**
     * 交换机
     */
    public static final String CONFIRM_SPRING_EXCHANGE = "CONFIRM_SPRING_EXCHANGE";

    /**
     * 队列
     */
    public static final String CONFIRM_SPRING_QUEUE = "CONFIRM_SPRING_QUEUE";

    /**
     * routingKey
     */
    public static final String CONFIRM_SPRING_ROUTING_KEY = "CONFIRM_SPRING_ROUTING_KEY";

    @Bean
    public DirectExchange confirmExchange(){
        return new DirectExchange(CONFIRM_SPRING_EXCHANGE, false, true);
    }

    @Bean
    public Queue confirmQueue(){
        return new Queue(CONFIRM_SPRING_QUEUE, false, false, true);
    }

    @Bean
    public Binding confirmBinding(Queue confirmQueue, DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_SPRING_ROUTING_KEY);
    }


}
