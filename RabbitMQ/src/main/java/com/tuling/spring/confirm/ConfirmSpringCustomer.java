package com.tuling.spring.confirm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     发布确认 - 消费者
 * @date 2022/4/27 3:13 下午
 */
@Component
public class ConfirmSpringCustomer {

    private Logger logger = LoggerFactory.getLogger(ConfirmSpringProducer.class);

    @RabbitListener(queues = ConfirmSpringConfig.CONFIRM_SPRING_QUEUE)
    public void customer(Message message){
        logger.info("当前时间：{}，接收消息：{}", new Date(), new String(message.getBody()));
    }

}
