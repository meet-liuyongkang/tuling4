package com.tuling.spring.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/28 11:02 上午
 */
@Component
public class ClusterSpringCustomer {

    private Logger logger = LoggerFactory.getLogger(ClusterSpringCustomer.class);

    @RabbitListener(queues = {ClusterSpringConfig.CLUSTER_SPRING_QUEUE})
    public void customer(Message message){
        logger.info("当前时间：{}，消费消息：{}", new Date(), new String(message.getBody()));
    }

}
