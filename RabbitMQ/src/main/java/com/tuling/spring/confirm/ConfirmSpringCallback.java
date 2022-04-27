package com.tuling.spring.confirm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/27 3:42 下午
 */
@Component
public class ConfirmSpringCallback implements RabbitTemplate.ConfirmCallback{

    private Logger logger = LoggerFactory.getLogger(ConfirmSpringProducer.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("当前时间：{}，发布确认回调接口：{}", new Date(), s);
    }
}
