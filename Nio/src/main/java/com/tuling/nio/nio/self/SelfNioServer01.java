package com.tuling.nio.nio.self;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 11:19
 *
 * 存在的问题：
 * 1.socket处理一次就会丢失
 */
public class SelfNioServer01 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 1.创建一个socket服务端
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            // 2.阻塞并等待客户端连接
            //TODO 假设这里不再阻塞
            Socket socket = serverSocket.accept();

            // 不阻塞的情况下，需要判断是否有socket连接进来
            if(socket != null){
                System.out.println("连接成功：" + socket.toString());


                // 既然每一个socket创建一个线程去处理不合适，那这里就不再创建线程。而且大家知道nio是单线程，那我们就往单线程上写。
//                new Thread(()->{
                    try {
                        // 3.阻塞并等待读取客户端发送的数据
//                        while (true) {
                            // TODO 假设这里也不再阻塞
                            DataInputStream dis = new DataInputStream(socket.getInputStream());
                            if (dis.available() > 0) {
                                System.out.println("收到消息：" + dis.readUTF());
                            }
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                }).start();
            }
        }

    }

}
