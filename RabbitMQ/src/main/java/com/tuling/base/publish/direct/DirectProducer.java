package com.tuling.base.publish.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 发布、订阅模式-生产者
 * 交换机类型=direct
 * 发布订阅模式，与工作队列模式类似，不同的是，这里是手动声明了一个交换机，然后进行交换机和队列的绑定。
 * 特点：
 *      1.交换机只会将消息发送给与交换机绑定的队列上，direct类型的交换机，在发送的时候，只会发送到routingKey相等的队列上。
 * @date 2022/4/19 3:53 下午
 */
public class DirectProducer {

    public static String QUEUE_NAME01 = "DirectQueue01";
    public static String QUEUE_NAME02 = "DirectQueue02";

    public static String ROUTING_KEY01 = "01";
    public static String ROUTING_KEY02 = "02";

    private static String EXCHANGE_NAME = "MyDirectExchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //声明一个队列
        channel.queueDeclare(QUEUE_NAME01, false, false, true, null);

        //声明一个队列
        channel.queueDeclare(QUEUE_NAME02, false, false, true, null);

        //绑定交换机和队列
        channel.queueBind(QUEUE_NAME01, EXCHANGE_NAME, ROUTING_KEY01);
        channel.queueBind(QUEUE_NAME02, EXCHANGE_NAME, ROUTING_KEY02);


        for (int i = 0; i < 10; i++) {
            String message = "消息"+i;
            if(i%2 == 0){
                System.out.println("消费者发送消息：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY01, null, message.getBytes());
            }else {
                System.out.println("消费者发送消息：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY02, null, message.getBytes());
            }

        }

    }

}
