package com.tuling.nio.nio.standard.version1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liuyongkang
 * @date Create in 2022/8/11 09:04
 */
public class NioServer11 {

    public static void main(String[] args) throws Exception {
        // 1.创建服务器管道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 2.管道默认为阻塞，手动设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 3.绑定ip和端口
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8080));

        // 4.创建一个多路复用器
        Selector selector = Selector.open();

        // 5.将管道注册到 选择器（多路复用器）中，并设置管道的监听状态为 OP_ACCEPT（接收连接）。注意，此时只是设置监听，这时并没有任何状态。
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("阻塞，并等待客户端连接");
        // 6.选择管道，此方法会阻塞，当至少选择一个管道，才会继续执行。（只要有一个客户端连接，就会存在一个管道，阻塞就会解除）
        selector.select();
        System.out.println("有客户端连接，阻塞解除");

        // 7.获取 选择器 中所有的状态
        Set<SelectionKey> selectionKeys = selector.selectedKeys();

        // 8.遍历选择器中的状态，根据不同的状态做不同的处理
        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
        while (keyIterator.hasNext()){
            SelectionKey next = keyIterator.next();

            // 9.判断当前是否为 accept 状态，只有服务端接口连接时，才会是该状态
            if (next.isAcceptable()) {
                SocketChannel accept = serverSocketChannel.accept();
                System.out.println("连接成功：" + accept.toString());

                // 将当前连接的管道的服务端也设置为非阻塞
                accept.configureBlocking(false);

                // 连接成功后，下一步是接收数据。但是，当前只是连接成功，并不知道客户端什么时候发送数据。
                // 而且现在不阻塞了，所以我们不能时刻去获取客户数据，这个时候应该怎么处理呢
                // 答案是监听，不阻塞的情况，也不确定客户什么时候发送数据，那我们应该设置一个监听，去监听客户端是否发送了数据。
                accept.register(selector, SelectionKey.OP_READ);
            } else if (next.isReadable()) {
                // 这是一个抽象类，只是用来表示选中的channel，我们直接将其转换给socketChannel即可
                SocketChannel socketChannel = (SocketChannel) next.channel();

                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer);
                String message =  new String(byteBuffer.array(), 0, byteBuffer.position());
                System.out.println("服务器收到消息：" + message);
            }
        }

    }

}
