package com.tuling.base.utils;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 获取连接的工具类
 * @date 2022/4/19 3:58 下午
 */
public class ConnectionUtil {

    /**
     * 单机配置
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.254.151");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        return connection.createChannel();

    }


    /**
     * 集群配置
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getClusterChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/");

        Address address02 = new Address("192.168.254.152", 5672);
        Address address03 = new Address("192.168.254.153", 5672);
        Address address04 = new Address("192.168.254.154", 5672);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address02);
        addresses.add(address03);
        addresses.add(address04);

        Connection connection = connectionFactory.newConnection(addresses);
        return connection.createChannel();

    }


    /**
     * haproxy 代理模式
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getProxyChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.254.131");
        connectionFactory.setPort(5673);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        return connection.createChannel();

    }

}
