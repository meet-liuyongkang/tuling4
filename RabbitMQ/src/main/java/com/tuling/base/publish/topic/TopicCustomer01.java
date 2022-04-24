package com.tuling.base.publish.topic;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 工作队列模式-消费者01
 * @date 2022/4/19 4:20 下午
 */
public class TopicCustomer01 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        DeliverCallback deliverCallback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(this.getClass().getSimpleName()+"收到队列（"+ TopicProducer.QUEUE_NAME02+"）消息: consumerTag=" + consumerTag + ",  body=" + new String(message.getBody()));
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            public void handle(String consumerTag) throws IOException {

            }
        };

        channel.basicConsume(TopicProducer.QUEUE_NAME02, false, deliverCallback, cancelCallback);
    }

}
