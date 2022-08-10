package com.tuling.nio.bio.version1;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author liuyongkang
 * @date Create in 2022/8/10 14:22
 */
public class BioClient11 {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        // 1.创建socket并连接到服务器
        Socket socket = new Socket("127.0.0.1", 8080);

        // 2.每隔五秒发送一条消息
        DataOutputStream dataOutputStream = null;
        for (int i = 0; i < 10; i++) {
            String message = "客户端A，第" + i + "次发送消息";
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(message);
            System.out.println("发送成功：" + message);

            Thread.sleep(5 * 1000);
        }

        // 3.关闭连接资源
        if (dataOutputStream!= null) {
            dataOutputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

}
