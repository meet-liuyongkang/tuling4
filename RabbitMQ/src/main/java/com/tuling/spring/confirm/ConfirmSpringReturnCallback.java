package com.tuling.spring.confirm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/27 3:42 下午
 */
@Component
public class ConfirmSpringReturnCallback implements RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(ConfirmSpringReturnCallback.class);

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("当前时间：{}，丢弃消息回调：{}", new Date(), new String(message.getBody()));
    }
}
