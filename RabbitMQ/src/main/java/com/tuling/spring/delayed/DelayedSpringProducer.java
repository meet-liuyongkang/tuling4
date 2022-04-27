package com.tuling.spring.delayed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     延时队列插件 - 生产者
 * @date 2022/4/27 10:51 上午
 */
@RestController
@RequestMapping("delayed")
public class DelayedSpringProducer {

    private Logger logger = LoggerFactory.getLogger(DelayedSpringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public void send(@RequestParam String message, Integer ttl){

        logger.info("当前时间：{}，延时：{}，发送消息：{}", new Date(), ttl, message);

        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(ttl);
                return message;
            }
        };

        rabbitTemplate.convertAndSend(DelayedSpringConfig.DELAYED_SPRING_EXCHANGE, DelayedSpringConfig.DELAYED_SPRING_ROUTING_KEY,
                message, messagePostProcessor);
    }

}
