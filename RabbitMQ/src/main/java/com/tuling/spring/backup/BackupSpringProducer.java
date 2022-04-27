package com.tuling.spring.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/27 4:10 下午
 */
@RestController
@RequestMapping("backup")
public class BackupSpringProducer {

    private Logger logger = LoggerFactory.getLogger(BackupSpringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public void send(@RequestParam String message){
        logger.info("当前时间：{}，发送消息：{}", new Date(), message);

        String routingKey = BackupSpringConfig.BACKUP_SPRING_NORMAL_ROUTING_KEY;
        if(message.contains("not")){
            routingKey = "aaa";
        }
        rabbitTemplate.convertAndSend(BackupSpringConfig.BACKUP_SPRING_NORMAL_EXCHANGE, routingKey, message);
    }

}
