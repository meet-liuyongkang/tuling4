package com.tuling.base.dead.max;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 死信队列 - 死信队列消费者
 * @date 2022/4/26 9:50 上午
 */
public class DeadMaxCustomer02 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(this.getClass().getSimpleName() + "消费消息：" + new String(message.getBody()));
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println(this.getClass().getSimpleName() + "取消订阅，consumerTag = " + consumerTag);
            }
        };

        channel.basicConsume(DeadMaxProducer.MAX_DEAL_QUEUE, true, deliverCallback, cancelCallback);
    }

}
