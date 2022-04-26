package com.tuling.spring.Ttl;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/26 7:42 下午
 */
@Component
public class TtlSpringDealCustomer {

    private Logger logger = LoggerFactory.getLogger(TtlSpringDealCustomer.class);

    @RabbitListener(queues = TtlSpringConfig.TTL_SPRING_DEAL_QUEUE)
    public void receive(Channel channel, Message message){
        logger.info("当前时间:{},死信队列收到消息:{}", new Date(), new String(message.getBody()));
    }

}
