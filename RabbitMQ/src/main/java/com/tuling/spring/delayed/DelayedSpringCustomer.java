package com.tuling.spring.delayed;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     延时队列插件 - 消费者
 * @date 2022/4/27 10:51 上午
 */
@Component
public class DelayedSpringCustomer {

    private Logger logger = LoggerFactory.getLogger(DelayedSpringCustomer.class);

    @RabbitListener(queues = DelayedSpringConfig.DELAYED_SPRING_QUEUE)
    public void customer(Channel channel, Message message){
        logger.info("当前时间：{}，接收消息：{}", new Date(), new String(message.getBody()));
    }

}
