package com.tuling.nio.nio.self;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 11:19
 *
 * 上一个版本存在的问题：socket最多只能读取一次消息，就会丢失
 *
 * 思路：通过一个容器，把所有的socket连接收集起来，然后去遍历这些socket，读取其中的数据
 */
public class SelfNioServer03 {

    private static List<SocketChannel> socketList;

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        socketList = new ArrayList<SocketChannel>();

        // 1.创建一个socket服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8080));

        while (true) {
            Thread.sleep(2 * 1000);

            // 2.阻塞并等待客户端连接
            //TODO 假设这里不再阻塞
            System.out.println("等待连接。。。");
            SocketChannel acceptSocket = serverSocketChannel.accept();

            // 不阻塞的情况下，需要判断是否有socket连接进来
            if (acceptSocket != null) {
                System.out.println("连接成功：" + acceptSocket.toString());

                // 将连接保存起来
                socketList.add(acceptSocket);
            }

            System.out.println("当前连接数：" + socketList.size());
            // 遍历所有的socket
            if (socketList != null && socketList.size() > 0) {
                for (SocketChannel socket : socketList) {
                    // 如果socket连接已经关闭，则将其移除
                        if(!socket.isConnected()){
                        socketList.remove(socket);
                    }

                    // 如果socket连接有发送数据，才进行处理
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socket.read(byteBuffer);
                    if (byteBuffer.position() > 0) {
                        // 一般业务比较复杂，所以这一步应该是单独用线程去处理。
                        // 不同的是，这里的线程是处理已经读取到的数据，所以这只是一个普通的业务线程，不会存在阻塞的情况。
                        // nio所说的单线程，其实指的是处理连接的线程，在Redis中，真实去执行命令的，也是单独的线程。
                        System.out.println("收到消息：" + new String(byteBuffer.array(), 0, byteBuffer.position()));
                    }
                }
            }
        }

    }

}
