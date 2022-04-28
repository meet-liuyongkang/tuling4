package com.tuling.spring.cluster;

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
 * @date 2022/4/28 11:02 上午
 */
@RestController
@RequestMapping("cluster")
public class ClusterSpringProducer {

    private Logger logger = LoggerFactory.getLogger(ClusterSpringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public void send(@RequestParam String message){
        logger.info("当前时间：{}，发送消息：{}", new Date(), message);

        rabbitTemplate.convertAndSend(ClusterSpringConfig.CLUSTER_SPRING_EXCHANGE, ClusterSpringConfig.CLUSTER_SPRING_ROUTING_KEY, message);
    }
}
