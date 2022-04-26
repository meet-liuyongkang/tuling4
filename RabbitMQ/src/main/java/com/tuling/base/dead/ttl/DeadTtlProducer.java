package com.tuling.base.dead.ttl;

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
 *
 * 设置死信队列有两步：
 * 1.设置死信交换机，key=x-dead-letter-exchange，是固定的，值就是死信交换机的名称
 * 2.设置消息转发到死信交换机时的routingKey，key=x-dead-letter-routing-key，是固定的，值就是死信队列与死信交换机绑定的routingKey，
 * 没有设置，或者设置的routingKey不匹配，死信队列就收不到消息了。
 *
 * @date 2022/4/26 9:50 上午
 */
public class DeadTtlProducer {

    public static String TTL_NORMAL_QUEUE = "TTL_NORMAL_QUEUE";
    public static String TTL_DEAL_QUEUE = "TTL_DEAL_QUEUE";

    public static String TTL_NORMAL_EXCHANGE = "TTL_NORMAL_EXCHANGE";
    public static String TTL_DEAL_EXCHANGE = "TTL_DEAL_EXCHANGE";

    public static String TTL_NORMAL_ROUTING_KEY = "normal";
    public static String TTL_DEAL_ROUTING_KEY = "deal";


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        // 声明一个普通交换机
        channel.exchangeDeclare(TTL_DEAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明一个普通队列
        channel.queueDeclare(TTL_DEAL_QUEUE, false, false, false, null);

        // 绑定普通队列
        channel.queueBind(TTL_DEAL_QUEUE, TTL_DEAL_EXCHANGE, TTL_DEAL_ROUTING_KEY);



        // 将上面声明的交换机设置为死信交换机
        Map<String,Object> params = new HashMap<>(1);
        params.put("x-dead-letter-exchange", TTL_DEAL_EXCHANGE);
        // 设置将消息转到死信交换机时的routingKey
        params.put("x-dead-letter-routing-key", TTL_DEAL_ROUTING_KEY);

        // 声明一个普通交换机
        channel.exchangeDeclare(TTL_NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明一个普通队列，将上面声明的交换机设置为这个队列的死信交换机
        channel.queueDeclare(TTL_NORMAL_QUEUE, false, false, false, params);

        // 绑定普通队列
        channel.queueBind(TTL_NORMAL_QUEUE, TTL_NORMAL_EXCHANGE, TTL_NORMAL_ROUTING_KEY);


        for (int i = 0; i < 5; i++) {
            String message = "消息" + i;
            System.out.println("生产者发送消息：" + message);

            // 获取properties的builder对象
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
            // 设置TTL过期时间为 10 秒
            builder.expiration("10000");
            // 调用build获取properties对象
            AMQP.BasicProperties basicProperties = builder.build();

            channel.basicPublish(TTL_NORMAL_EXCHANGE, TTL_NORMAL_ROUTING_KEY, basicProperties, message.getBytes());
        }

    }

}
