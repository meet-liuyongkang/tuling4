package com.tuling.spring.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/27 4:26 下午
 */
@Component
public class BackupSpringCustomer {

    private Logger logger = LoggerFactory.getLogger(BackupSpringCustomer.class);

    @RabbitListener(queues = BackupSpringConfig.BACKUP_SPRING_NORMAL_QUEUE)
    public void customer(Message message){
        logger.info("当前时间：{}，接收消息：{}", new Date(), new String(message.getBody()));
    }


    @RabbitListener(queues = BackupSpringConfig.BACKUP_SPRING_QUEUE)
    public void warn(Message message){
        logger.info("当前时间：{}，警告！消息被丢弃：{}", new Date(), new String(message.getBody()));
    }

}
