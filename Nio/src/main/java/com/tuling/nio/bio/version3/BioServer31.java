package com.tuling.nio.bio.version3;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 11:19
 *
 * 优化后：可以处理多个连接。
 *
 * 缺点：
 * 1.每个socket连接都需要占用一个线程，如果在高并发场景，则会开启大量的线程。
 * 2.这些线程并不是时刻都在接受数据，所以会造成服务器的资源浪费。
 *   并且绝大多数线程在某个时间段内并不是有效线程，例如有些线程并不是一连上来就会发送数据，有些线程是间隔一段时间发送一次数据。那么在
 *   没有发送数据的这个时间段，服务器上的线程只是一个空闲线程，这样会造成大量的服务器资源被浪费。
 */
public class BioServer31 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 1.创建一个socket服务端
        ServerSocket serverSocket = new ServerSocket(8080);

        List<Socket> socketList = new ArrayList<>();

        while (true) {
            // 2.阻塞并等待客户端连接
            // 假设不阻塞
            Socket socket = serverSocket.accept();
            System.out.println("连接成功：" + socket.toString());

            if(socket == null){
                System.out.println("等待连接");
            } else {
                socketList.add(socket);
            }

            for (Socket socket1 : socketList){
                // 假设不阻塞
                DataInputStream dis = new DataInputStream(socket1.getInputStream());
                if (dis.available() > 0) {
                    System.out.println("收到消息：" + dis.readUTF());
                }
                if(!socket1.isConnected()){
                    socketList.remove(socket1);
                }
            }


        }

    }

}
