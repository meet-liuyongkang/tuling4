package com.tuling.base.publish.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 发布、订阅模式-生产者
 * 交换机类型=Topic
 * 特点：
 *      1. Topic交换机在绑定队列时需要指定routingKey，与direct交换机不同的时，topic交换机的routingKey支持正则匹配。
 *      2. Topic交换机只会将消息发送到绑定的队列，并且routingKey能匹配上的队列中。
 *      3. 匹配规则：*表示匹配一个单词。#表示匹配一个或多个单词。  单词间通过.分隔。
 * @date 2022/4/19 3:53 下午
 */
public class TopicProducer {

    public static String QUEUE_NAME02 = "TopicQueue02";

    public static String QUEUE_NAME0203 = "TopicQueue0203";

    public static String QUEUE_NAME020305 = "TopicQueue020305";

    public static String QUEUE_NAME0305 = "TopicQueue0305";

    public static String ROUTING_KEY02 = "02.03.05";

    public static String ROUTING_KEY0203 = "02.03";

    public static String ROUTING_KEY0305 = "03.05";


    private static String EXCHANGE_NAME = "MyTopicExchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //声明一个队列
        channel.queueDeclare(QUEUE_NAME02, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME0203, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME0305, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME020305, false, false, true, null);

        //绑定交换机和队列
        channel.queueBind(QUEUE_NAME02, EXCHANGE_NAME, ROUTING_KEY02);
        channel.queueBind(QUEUE_NAME0203, EXCHANGE_NAME, "02.#");
        channel.queueBind(QUEUE_NAME020305, EXCHANGE_NAME, "*.*.05");
        channel.queueBind(QUEUE_NAME0305, EXCHANGE_NAME, "*.05");


        for (int i = 0; i < 16; i++) {
            String message = "消息"+i;
            if(i%2 == 0){
                System.out.println("消费者发送消息，routingKey（" + ROUTING_KEY02 + "）：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY02, null, message.getBytes());
            }
            if(i%3 == 0) {
                System.out.println("消费者发送消息，routingKey（"+ROUTING_KEY0203 + "）：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY0203, null, message.getBytes());
            }
            if(i%5 == 0) {
                System.out.println("消费者发送消息，routingKey（"+ROUTING_KEY0305 + "）：" + message);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY0305, null, message.getBytes());
            }

        }

    }

}
