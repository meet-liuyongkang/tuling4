package com.tuling.nio.nio.self;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 11:19
 *
 * 前面Bio代码 BioServer31 通过多线程来处理多个连接存在的问题：
 * 1. 为每一个连接都创建一个线程，但是这些线程并不是时刻都在发送数据，这样会造成大量的服务器资源浪费。
 *
 * 解决思路：
 * 1.既然多线程不可取，那就不用多线程了。不用多线程就回到了 BioServer21 版本，这个版本的问题是：
 *      1.1 read 方法会阻塞主线程，导致服务端无法处理新的连接。（思路：假设这里不阻塞）
 *      1.2 accept 方法会阻塞主线程，导致如果没有新的连接，主程序会阻塞，无法处理之前连接的客户端发送的数据。（思路：假设这里不阻塞）
 *      1.3 一个连接处理完后，没有保存，客户端连接丢失了，就无法再处理这个客户端发送的数据。（思路：使用一个容器保存所有的连接）
 *
 *
 * 小结：当前版本已经可以通过单线程来处理多个连接了，但是还需要将连接保存起来，我们看下一个版本。
 */
public class SelfNioServer01 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 创建一个socket服务端
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            // TODO 3. 假设这里不再阻塞
            Socket socket = serverSocket.accept();

            // 不阻塞的情况下，需要判断是否有socket连接进来
            if(socket != null){
                System.out.println("连接成功：" + socket.toString());


                // TODO 1. 既然每一个socket创建一个线程去处理不合适，那这里就不再创建线程。而且大家知道nio是单线程，那我们就往单线程上写。
//                new Thread(()->{
                    try {
//                        while (true) {
                            // TODO 2. 假设这里也不再阻塞
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
