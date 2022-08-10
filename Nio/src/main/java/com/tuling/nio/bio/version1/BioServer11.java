package com.tuling.nio.bio.version1;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 11:19
 *
 * 缺点：
 * 1.只能处理一个连接。
 * 2.当连接发送一条消息后，程序执行完毕，连接被断开。
 */
public class BioServer11 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 1.创建一个socket服务端
        ServerSocket serverSocket = new ServerSocket(8080);

        // 2.阻塞并等待客户端连接
        Socket socket = serverSocket.accept();
        System.out.println("连接成功：" + socket.toString());

        // 3.阻塞并等待读取客户端发送的数据
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        System.out.println("收到消息：" + dis.readUTF());

    }

}
