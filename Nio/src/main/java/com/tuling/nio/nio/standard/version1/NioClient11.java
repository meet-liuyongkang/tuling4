package com.tuling.nio.nio.standard.version1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author liuyongkang
 * @date Create in 2022/8/11 14:44
 */
public class NioClient11 {

    public static void main(String[] args) throws Exception {
        // 1.获取一个socket管道对象
        SocketChannel socketChannel = SocketChannel.open();

        // 2.设置为非阻塞
        socketChannel.configureBlocking(false);

        // 3.创建一个选择器
        Selector selector = Selector.open();

        // 4.将管道注册到 选择器（多路复用器）上，并监听连接
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        // 5.发起连接
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        // 6.发送数据
        socketChannel.write(ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8)));

    }

}
