package com.tuling.spring.Ttl;

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
 * @description ttl消息生产者
 * @date 2022/4/26 7:33 下午
 */
@RestController
@RequestMapping("ttl")
public class TtlSpringProducer {

    private Logger logger = LoggerFactory.getLogger(TtlSpringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public void send(@RequestParam String message){
        logger.info("当前时间:{},发送一条消息给队列：{}", new Date(), message);

        // 发送消息给普通队列
        rabbitTemplate.convertAndSend(TtlSpringConfig.TTL_SPRING_NORMAL_EXCHANGE, TtlSpringConfig.TTL_SPRING_NORMAL_ROUTING_KEY, message);
    }

}
