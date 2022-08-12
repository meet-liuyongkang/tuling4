package com.tuling.nio.aio.version01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author liuyongkang
 * @date Create in 2022/8/12 10:06
 */
public class AioServer01 {

    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open();

        assc.bind(new InetSocketAddress("127.0.0.1", 8080));


        CountDownLatch countDownLatch = new CountDownLatch(1);

        assc.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            // 连接成功的回调
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {


                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                result.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
                    // 数据读取成功回调
                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.position()));
                    }

                    // 数据读取失败回调
                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("Aio数据接收失败！" + exc.getMessage());
                    }
                });
                System.out.println("Aio服务器接收到消息：" + new String(byteBuffer.array(), 0, byteBuffer.position()));

                countDownLatch.countDown();
            }

            // 连接失败的回调
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("Aio服务器连接失败！" + exc.getMessage());

                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
    }

}
