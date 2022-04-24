package com.tuling.base.qos;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 限流配置-消费者
 * @date 2022/4/19 3:09 下午
 */
public class QosCustomer01 {

    public static void main(String[] args) throws IOException, TimeoutException {

        final Channel channel = ConnectionUtil.getChannel();

        // 消息本身的大小 如果设置为0  那么表示对消息本身的大小不限制
        int prefetchSize = 0;

        // 告诉rabbitmq不要一次性给消费者推送大于N个消息，所有设置此值后，处理消息更快的消费者，能处理更多的消息。
        // 默认值为0，使用轮询的方式，将消息平均分配给所有的消费者
        int prefetchCount = 2;

        // 是否将上面的设置应用于整个通道，false表示只应用于当前消费者，信道的设置既可以在生产者端设置，也可以在消费者端设置
        boolean global = true;

        // 需要注意的是，Qos设置必须同时将消费者的自定应答设置为false，才会生效。
        // 因为自动应答为true时，每次给消费者发消息，都是瞬间被应答，也就无法区分消费快和慢的消费者。
        channel.basicQos(prefetchSize, prefetchCount, global);

        //消费消息的回调接口
        DeliverCallback deliverCallback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery message) throws IOException {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("01收到消息: consumerTag=" + consumerTag + ",  body=" + new String(message.getBody()));

                /**
                 * 手动应答消息
                 *
                 * basicAck 消息参数说明：
                 * 1. deliveryTag：当前消息的tag
                 * 2. multiple：是否批量应答
                 */
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };

        //取消消息的回调接口，除了调用 channel.basicCancel 方法取消订阅，其他任何形式取消订阅，都会回调此接口
        CancelCallback cancelCallback = new CancelCallback() {
            public void handle(String consumerTag) throws IOException {
                System.out.println("取消了消息回调：" + consumerTag);
            }
        };


        /**
         * 消费消息
         * 1.队列名称
         * 2.是否自动应答
         * 3.处理消息的回调接口
         * 4.取消消息的回调接口
         */
        // TODO 只有autoAck设置为false，那么设置的Qos（消息数量限制）才会生效
        channel.basicConsume(QosProducer.QUEUE_NAME, false, deliverCallback, cancelCallback);

    }

}
