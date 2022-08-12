package com.tuling.nio.nio.standard.version4;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author liuyongkang
 * @date Create in 2022/8/11 14:44
 */
public class NioClient41 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 1.获取一个socket管道对象
        SocketChannel socketChannel = SocketChannel.open();

        // 2.设置为非阻塞
        socketChannel.configureBlocking(false);

        // 3.创建一个选择器
        Selector selector = Selector.open();

        // 4.将管道注册到 选择器（多路复用器）上，并监听连接
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        // 5.发起连接（注意，这里只是连接上了服务器，但是管道并没有连通，哪个管道与服务器连通，由选择器来控制）
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        while (true){

            // 6.选择管道，如果没有管道，则会阻塞。至少存在一个管道，才会解除阻塞。由于上面客户端已经发起了连接，所以选择器中至少会有一个管道，这里也就不会阻塞。
            selector.select();

            // 7.遍历选择器中的所有管道
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();

                // 判断连接是否建立成功
                if (next.isConnectable()) {
                    // 这个时候客户端显示的是 （连接挂起）
                    System.out.println("当前客户端连接信息：" + socketChannel.toString());

                    // 如果连接是挂起的时候，我们就可以将管道与服务器进行连通。
                    if(socketChannel.isConnectionPending()){
                        socketChannel.finishConnect();
                        System.out.println("当前客户端连接信息：" + socketChannel.toString());

                        // 只有管道连通之后，才能够写数据
                        String message = "hello";
                        System.out.println("客户端发送消息：" + message);

                        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
                        socketChannel.write(byteBuffer);

                    }
                }

            }

        }

    }

}
