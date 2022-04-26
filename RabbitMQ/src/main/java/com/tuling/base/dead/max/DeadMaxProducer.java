package com.tuling.base.dead.max;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description  死信队列 - 队列已满
 *
 * params.put("x-max-length", 5); 队列中最多只能存放5条消息，当消息没有被消费时，消息超过队列长度，默认将最早的消息丢弃或转给死信队列。
 * qos设置，只要消费者连接了，并且没有设置连接接收消息的限制，那么消息就会无限发送给消费者，交换机队列永远不会放满，因为消息马上就发给了消费者。
 *
 * @date 2022/4/26 9:50 上午
 */
public class DeadMaxProducer {

    public static String MAX_NORMAL_QUEUE = "MAX_NORMAL_QUEUE";
    public static String MAX_DEAL_QUEUE = "MAX_DEAL_QUEUE";

    public static String MAX_NORMAL_EXCHANGE = "MAX_NORMAL_EXCHANGE";
    public static String MAX_DEAL_EXCHANGE = "MAX_DEAL_EXCHANGE";

    public static String MAX_NORMAL_ROUTING_KEY = "normal";
    public static String MAX_DEAL_ROUTING_KEY = "deal";


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        // 声明一个普通交换机
        channel.exchangeDeclare(MAX_DEAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明一个普通队列
        channel.queueDeclare(MAX_DEAL_QUEUE, false, false, false, null);

        // 绑定普通队列
        channel.queueBind(MAX_DEAL_QUEUE, MAX_DEAL_EXCHANGE, MAX_DEAL_ROUTING_KEY);



        // 将上面声明的交换机设置为死信交换机
        Map<String,Object> params = new HashMap<>(1);
        params.put("x-dead-letter-exchange", MAX_DEAL_EXCHANGE);
        // 设置将消息转到死信交换机时的routingKey
        params.put("x-dead-letter-routing-key", MAX_DEAL_ROUTING_KEY);

        // 设置队列的长度
        params.put("x-max-length", 5);



        // 声明一个普通交换机
        channel.exchangeDeclare(MAX_NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明一个普通队列，将上面声明的交换机设置为这个队列的死信交换机
        channel.queueDeclare(MAX_NORMAL_QUEUE, false, false, false, params);

        // 绑定普通队列
        channel.queueBind(MAX_NORMAL_QUEUE, MAX_NORMAL_EXCHANGE, MAX_NORMAL_ROUTING_KEY);

        for (int i = 0; i < 20; i++) {
            String message = "消息" + i;
            System.out.println("生产者发送消息：" + message);
            channel.basicPublish(MAX_NORMAL_EXCHANGE, MAX_NORMAL_ROUTING_KEY, null, message.getBytes());
        }

    }

}
