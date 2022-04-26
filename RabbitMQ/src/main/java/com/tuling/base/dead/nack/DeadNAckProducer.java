package com.tuling.base.dead.nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description  死信队列 - TTL过期
 * @date 2022/4/26 9:50 上午
 */
public class DeadNAckProducer {

    public static String NACK_NORMAL_QUEUE = "NACK_NORMAL_QUEUE";
    public static String NACK_DEAL_QUEUE = "NACK_DEAL_QUEUE";

    public static String NACK_NORMAL_EXCHANGE = "NACK_NORMAL_EXCHANGE";
    public static String NACK_DEAL_EXCHANGE = "NACK_DEAL_EXCHANGE";

    public static String NACK_NORMAL_ROUTING_KEY = "normal";
    public static String NACK_DEAL_ROUTING_KEY = "deal";


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        // 声明一个普通交换机
        channel.exchangeDeclare(NACK_DEAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明一个普通队列
        channel.queueDeclare(NACK_DEAL_QUEUE, false, false, false, null);

        // 绑定普通队列
        channel.queueBind(NACK_DEAL_QUEUE, NACK_DEAL_EXCHANGE, NACK_DEAL_ROUTING_KEY);



        // 将上面声明的交换机设置为死信交换机
        Map<String,Object> params = new HashMap<>(1);
        params.put("x-dead-letter-exchange", NACK_DEAL_EXCHANGE);
        params.put("x-dead-letter-routing-key", NACK_DEAL_ROUTING_KEY);

        // 声明一个普通交换机
        channel.exchangeDeclare(NACK_NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明一个普通队列，将上面声明的交换机设置为这个队列的死信交换机
        channel.queueDeclare(NACK_NORMAL_QUEUE, false, false, false, params);

        // 绑定普通队列
        channel.queueBind(NACK_NORMAL_QUEUE, NACK_NORMAL_EXCHANGE, NACK_NORMAL_ROUTING_KEY);


        for (int i = 0; i < 10; i++) {
            String message = "消息" + i;
            System.out.println("生产者发送消息：" + message);

            // 获取properties的builder对象
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
            // 设置TTL过期时间为 10 秒
            builder.expiration("5000");
            // 调用build获取properties对象
            AMQP.BasicProperties basicProperties = builder.build();

            channel.basicPublish(NACK_NORMAL_EXCHANGE, NACK_NORMAL_ROUTING_KEY, basicProperties, message.getBytes());
        }

    }

}
