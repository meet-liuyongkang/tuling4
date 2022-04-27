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
 * @description 通过死信实现的延时队列 - 配置类
 *
 * 通过死信自己实现的延时队列有个问题。设置消息的过期时间（TTL = time_to_live）有两种方式：
 * 1. 通过 params.put("x-message-ttl", 10000); 设置整个队列中消息的过期时间。
 * 2. 通过发送消息时，单独给消息设置TTL
 * 上面两种设置同时存在时，TTL时间短的优先。
 *
 *      第二种方式，单独给消息设置TTL存在一个问题，那就是队列每次只会检查第一条消息的TTL。举个例子，队列第一条消息超时时间为30分钟，
 * 第二条消息超时时间为10秒，那么队列只会检查第一条消息的TTL，第二条消息即时过期了，也不会马上被丢弃或者交给死信队列，只有第一条消息
 * 的时间到了，将第一条消息丢弃/死信 才会处理后面的消息，就会导致第二条只能存活10秒的消息存活了30分钟。
 *      为了解决这个问题，我们可以使用rabbitmq的延时队列插件来处理。
 *
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
