package com.tuling.base.publish.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 发布、订阅模式-生产者
 * 交换机类型=fanout
 * 特点：
 *      1. fanout交换机在绑定队列时不需要指定routingKey，即使指定了，也不会生效。
 *      2. fanout交换机会将消息发送到所有与交换机绑定的队列中，也就是说，与此交换机绑定的队列，都会收到所有的消息。
 * @date 2022/4/19 3:53 下午
 */
public class FanoutProducer {

    public static String QUEUE_NAME01 = "FanoutQueue01";

    public static String QUEUE_NAME02 = "FanoutQueue02";

    public static String ROUTING_KEY01 = "01";

    public static String ROUTING_KEY02 = "02";


    private static String EXCHANGE_NAME = "MyFanoutExchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        //声明一个队列
        channel.queueDeclare(QUEUE_NAME01, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME02, false, false, true, null);

        //绑定交换机和队列
        channel.queueBind(QUEUE_NAME01, EXCHANGE_NAME, ROUTING_KEY01);
        channel.queueBind(QUEUE_NAME02, EXCHANGE_NAME, ROUTING_KEY02);


        for (int i = 0; i < 10; i++) {
            String message = "消息"+i;
            if(i%2 == 0){
                System.out.println("消费者发送消息，routingKey（"+ROUTING_KEY01+"）：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY01, null, message.getBytes());
            }else {
                System.out.println("消费者发送消息，routingKey（"+ROUTING_KEY02 + "123"+"）：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY02 + "123", null, message.getBytes());
            }

        }

    }

}
