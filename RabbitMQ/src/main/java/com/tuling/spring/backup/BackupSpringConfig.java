package com.tuling.spring.backup;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     备份交换机 - 配置
 * @date 2022/4/27 4:10 下午
 */
@Configuration
public class BackupSpringConfig {

    public static final String BACKUP_SPRING_EXCHANGE = "BACKUP_SPRING_EXCHANGE";

    public static final String BACKUP_SPRING_QUEUE = "BACKUP_SPRING_QUEUE";



    public static final String BACKUP_SPRING_NORMAL_EXCHANGE = "BACKUP_SPRING_NORMAL_EXCHANGE";

    public static final String BACKUP_SPRING_NORMAL_QUEUE = "BACKUP_SPRING_NORMAL_QUEUE";

    public static final String BACKUP_SPRING_NORMAL_ROUTING_KEY = "BACKUP_SPRING_NORMAL_ROUTING_KEY";

    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_SPRING_EXCHANGE, false, true);
    }

    @Bean
    public Queue backupQueue(){
        return new Queue(BACKUP_SPRING_QUEUE, false, false, true);
    }

    @Bean
    public Binding bindingBackup(Queue backupQueue, FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }


    @Bean
    public DirectExchange normalExchange(){
        // 设置备份交换机
        Map<String, Object> params = new HashMap<>(1);
        params.put("alternate-exchange", BACKUP_SPRING_EXCHANGE);

        return new DirectExchange(BACKUP_SPRING_NORMAL_EXCHANGE, false, true, params);
    }

    @Bean
    public Queue normalQueue(){
        return new Queue(BACKUP_SPRING_NORMAL_QUEUE, false, false, true);
    }

    @Bean
    public Binding normalBinding(Queue normalQueue, DirectExchange normalExchange){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with(BACKUP_SPRING_NORMAL_ROUTING_KEY);
    }


}
