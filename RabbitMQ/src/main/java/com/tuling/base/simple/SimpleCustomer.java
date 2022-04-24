package com.tuling.base.simple;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 简单模式-消费者
 * @date 2022/4/19 3:09 下午
 */
public class SimpleCustomer {

    /**
     * 队列名称
     */
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


        //消费消息的回调接口
        DeliverCallback deliverCallback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("消费者收到消息: consumerTag=" + consumerTag + ",  body=" + new String(message.getBody()));
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
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);

    }

}
