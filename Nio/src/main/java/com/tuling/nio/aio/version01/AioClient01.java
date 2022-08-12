package com.tuling.nio.aio.version01;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @author liuyongkang
 * @date Create in 2022/8/12 10:25
 */
public class AioClient01 {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel asc = AsynchronousSocketChannel.open();

        asc.connect(new InetSocketAddress("127.0.0.1", 8080), null, new CompletionHandler<Void, Object>() {
            // 连接成功执行
            @Override
            public void completed(Void result, Object attachment) {
                String message = "客户端A，发送了消息";
                System.out.println(message);
                asc.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
            }

            // 连接失败执行
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("发送消息异常！" + exc.getMessage());
            }
        });

        Thread.sleep(60 * 1000);
    }

}
