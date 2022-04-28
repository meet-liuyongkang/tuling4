package com.tuling.spring.cluster;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     rabbitmq集群 - 配置类
 * @date 2022/4/28 11:02 上午
 */
@Configuration
public class ClusterSpringConfig {

    public static final String CLUSTER_SPRING_EXCHANGE = "CLUSTER_SPRING_EXCHANGE";

    public static final String CLUSTER_SPRING_QUEUE = "CLUSTER_SPRING_QUEUE";

    public static final String CLUSTER_SPRING_ROUTING_KEY = "CLUSTER_SPRING_ROUTING_KEY";

    @Bean
    public DirectExchange clusterExchange(){
        return new DirectExchange(CLUSTER_SPRING_EXCHANGE, false, true);
    }

    @Bean
    public Queue clusterQueue(){
        return new Queue(CLUSTER_SPRING_QUEUE, false, false, true);
    }

    @Bean
    public Binding clusterBinding(Queue clusterQueue, DirectExchange clusterExchange){
        return BindingBuilder.bind(clusterQueue).to(clusterExchange).with(CLUSTER_SPRING_ROUTING_KEY);
    }

}
