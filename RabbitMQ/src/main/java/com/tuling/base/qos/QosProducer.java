package com.tuling.base.qos;

import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 限流配置-生产者
 *
 * 1. 必须开启手动应答，限流设置才会生效。因为自动应答时，消息都是瞬间自动被应答，也就无法区分消费快和慢的消费者。
 *
 * @date 2022/4/19 2:16 下午
 */
public class QosProducer {

    /**
     * 队列名称
     */
    public static String QUEUE_NAME = "DistributionQueue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = ConnectionUtil.getChannel();

        /**
         * 声明一个队列，参数说明：
         * 1. queue：队列名称
         * 2. durable：是否为持久队列，如果为是，该队列将在服务器重启后依然存在。exclusive=true时，此设置失效。
         * 3. exclusive：是否为独占队列。如果是，1.仅当前连接可见，只区分连接，不区分信道。 2.会在连接断开时自动删除。
         * 4. autoDelete：是否自动删除，如果为是，那么在所有绑定该队列的连接断开后会自动删除队列。
         * 5. argument：其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        for (int i = 0; i < 20; i++) {

            String message = "消息"+i;
            System.out.println("生产者发送消息：" + message);

            /**
             * 发送消息，参数说明：
             * 1. exchange：指定交换机
             * 2. routingKey：指定路由key
             * 3. props：消息的其他属性 - 路由标头等
             * 4. body：消息内容
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }

    }

}
