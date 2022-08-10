package com.tuling.nio.bio.version2;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 11:19
 *
 * 优化后：特殊情况下可以处理多个连接。
 *
 * 缺点：
 * 1.每个socket的消息只被处理一次，然后socket就会丢失，无法处理后续的消息。
 * 2.如果第一个连接的socket一只不发送消息，那么线程会被阻塞在读取消息这里，导致后续的socket连接被阻塞。
 */
public class BioServer21 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 1.创建一个socket服务端
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            // 2.阻塞并等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("连接成功：" + socket.toString());

            // 3.阻塞并等待读取客户端发送的数据
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println("收到消息：" + dis.readUTF());
        }

    }

}
