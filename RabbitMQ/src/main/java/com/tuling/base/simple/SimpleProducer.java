package com.tuling.base.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 简单模式-生产者
 * 简单模式，也就是一个生产者对应一个消费者，点对点的生产、消费消息。
 * 不需要声明交换机，这里使用的默认交换机，默认交换机是direct类型。
 * @date 2022/4/19 2:16 下午
 */
public class SimpleProducer {

    private static String QUEUE_NAME = "SimpleQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.254.151");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");

        // 创建连接，连接是TCP连接，并发量大的情况下，频繁创建TCP连接消耗过大，而且服务器的TCP连接数据也有限制，
        // 所以通过在TCP连接中开辟信channel（信道）的方式，解决上面的这些限制
        Connection connection = connectionFactory.newConnection();

        // 创建一个信道，信道是通过在TCP连接中创建的，每个信道都是一个数据传输的通道
        Channel channel = connection.createChannel();


        /**
         * 声明一个队列，参数说明：
         * 1. queue：队列名称
         * 2. durable：是否为持久队列，如果为是，该队列将在服务器重启后依然存在。exclusive=true时，此设置失效。
         * 3. exclusive：是否为独占队列。如果是，1.仅当前连接可见，只区分连接，不区分信道。 2.会在连接断开时自动删除。
         * 4. autoDelete：是否自动删除，如果为是，那么在所有绑定该队列的连接断开后会自动删除队列。
         * 5. argument：其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();

            /**
             * 发送消息，参数说明：
             * 1. exchange：指定交换机
             * 2. routingKey：指定路由key
             * 3. props：消息的其他属性 - 路由标头等
             * 4. body：消息内容
             */
            channel.basicPublish("", QUEUE_NAME, null, next.getBytes());
        }

    }

}
