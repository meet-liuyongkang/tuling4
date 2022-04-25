package com.tuling.base.confirm;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     发布确认 - 消费者
 * @date 2022/4/24 2:24 下午
 */
public class ConfirmCustomer02 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        // 消费消息的回调接口
        final DeliverCallback deliverCallback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(this.getClass().getSimpleName() + "接收消息：" + new String(message.getBody()));
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            public void handle(String consumerTag) throws IOException {
                System.out.println("调用了取消回调接口：" + consumerTag);
            }
        };

        channel.basicConsume(ConfirmProducer.Queue_name, true, deliverCallback, cancelCallback);

    }

}
