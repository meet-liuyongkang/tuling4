package com.tuling.spring.confirm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     发布确认 - 生产者
 * @date 2022/4/27 3:13 下午
 */
@RestController
@RequestMapping("confirm")
public class ConfirmSpringProducer {

    private Logger logger = LoggerFactory.getLogger(ConfirmSpringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfirmSpringCallback callback;

    @Autowired
    private ConfirmSpringReturnCallback returnCallback;

    @PostConstruct
    public void init(){
        // 设置发布确认回调对象
        rabbitTemplate.setConfirmCallback(callback);

        // 开启消息丢失回调 （仅在开启了发布确认的情况下才会生效）
        rabbitTemplate.setMandatory(true);

        // 设置消息丢失回调对象
        rabbitTemplate.setReturnCallback(returnCallback);
    }


    @GetMapping("send")
    public void send(@RequestParam String message){
        logger.info("当前时间：{}，发送消息：{}", new Date(), message);

        String routingKey = ConfirmSpringConfig.CONFIRM_SPRING_ROUTING_KEY;

        // 包含not的消息将找不到匹配的队列而被丢弃
        if(message.contains("not")){
            routingKey = "aaa";
        }
        rabbitTemplate.convertAndSend(ConfirmSpringConfig.CONFIRM_SPRING_EXCHANGE, routingKey, message);
    }

}
