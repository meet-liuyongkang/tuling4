package com.tuling.spring.Ttl;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/26 5:59 下午
 */
@Configuration
public class TtlSpringConfig {

    /**
     * 普通交换机
     */
    public static final String TTL_SPRING_NORMAL_EXCHANGE = "TTL_SPRING_NORMAL_EXCHANGE";

    /**
     * 普通队列
     */
    public static final String TTL_SPRING_NORMAL_QUEUE = "TTL_SPRING_NORMAL_QUEUE";

    /**
     * 普通routingKey
     */
    public static final String TTL_SPRING_NORMAL_ROUTING_KEY = "normal";

    /**
     * 死信交换机
     */
    public static final String TTL_SPRING_DEAL_EXCHANGE = "TTL_SPRING_DEAL_EXCHANGE";

    /**
     * 死信队列
     */
    public static final String TTL_SPRING_DEAL_QUEUE = "TTL_SPRING_DEAL_QUEUE";

    /**
     * 死信routingKey
     */
    public static final String TTL_SPRING_DEAL_ROUTING_KEY = "deal";


    /**
     * 声明死信交换机
     * @return
     */
    @Bean
    public DirectExchange ttlSpringDealExchange(){
        return new DirectExchange(TTL_SPRING_DEAL_EXCHANGE, false, true);
    }

    /**
     * 声明死信队列
     * @return
     */
    @Bean
    public Queue ttlSpringDealQueue(){
        return new Queue(TTL_SPRING_DEAL_QUEUE);

    }

    /**
     * 死信队列和死信交换机绑定
     * @return
     */
    @Bean
    public Binding dealBinding(Queue ttlSpringDealQueue, DirectExchange ttlSpringDealExchange){
        return BindingBuilder.bind(ttlSpringDealQueue).to(ttlSpringDealExchange).with(TTL_SPRING_DEAL_ROUTING_KEY);
    }


    /**
     * 声明普通交换机
     * @return
     */
    @Bean("ttlSpringNormalExchange")
    public DirectExchange ttlSpringNormalExchange(){
        return new DirectExchange(TTL_SPRING_NORMAL_EXCHANGE, false, true);
    }

    /**
     * 声明普通队列
     * @return
     */
    @Bean("ttlSpringNormalQueue")
    public Queue ttlSpringNormalQueue(){
        Map<String, Object> params =  new HashMap<>();
        // 设置死信交换机
        params.put("x-dead-letter-exchange", TTL_SPRING_DEAL_EXCHANGE);
        // 设置死信routingKey
        params.put("x-dead-letter-routing-key", TTL_SPRING_DEAL_ROUTING_KEY);
        // 设置队列中消息的超时时间
        params.put("x-message-ttl", 10000);

        return QueueBuilder.durable(TTL_SPRING_NORMAL_QUEUE).withArguments(params).build();
    }

    /**
     * 普通队列和普通交换机绑定
     * @return
     */
    @Bean
    public Binding normalBinding(Queue ttlSpringNormalQueue, DirectExchange ttlSpringNormalExchange){
        return BindingBuilder.bind(ttlSpringNormalQueue).to(ttlSpringNormalExchange).with(TTL_SPRING_NORMAL_ROUTING_KEY);
    }

}
