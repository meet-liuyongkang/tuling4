package com.tuling.base.dead.nack;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 死信队列 - 普通队列消费者
 * @date 2022/4/26 9:50 上午
 */
public class DeadNAckCustomer01 {

    public static void main(String[] args) throws IOException, TimeoutException {
        AtomicInteger nackCount = new AtomicInteger(0);

        Channel channel = ConnectionUtil.getChannel();

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {


                String messageStr = new String(message.getBody());
                String substring = messageStr.substring(messageStr.length() - 1);
                int i = Integer.parseInt(substring);
                if(i%2 == 0){
                    System.out.println(getClass().getSimpleName() + "消费消息：" + messageStr);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } else {
                    if(i == 1){
                        System.out.println("消息（"+ messageStr +"）被拒绝：" + nackCount.addAndGet(1) + "，时间：" + System.currentTimeMillis());
                    }
                    // 参数说明requeue是否重新入队，如果为true，那么消息拒绝后会重新放入队列，而不是丢弃/死信处理
                    channel.basicNack(message.getEnvelope().getDeliveryTag(), false, false);
                }
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println(getClass().getSimpleName() + "取消订阅，consumerTag = " + consumerTag);
            }
        };

        channel.basicConsume(DeadNAckProducer.NACK_NORMAL_QUEUE, false, deliverCallback, cancelCallback);
    }

}
