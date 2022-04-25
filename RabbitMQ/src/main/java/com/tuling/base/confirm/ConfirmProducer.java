package com.tuling.base.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 发布确认 - 生产者
 * @date 2022/4/24 2:11 下午
 */
public class ConfirmProducer {

    public static String Queue_name = "ConfirmQueue";

    public static int batchConfirmCount = 20;

    public static int sendCount = 1000;


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        singleConfirm();

        Thread.sleep(3000);

        batchConfirm();

        Thread.sleep(3000);

        syncConfirm();
    }


    /**
     * 单个确认
     */
    public static void singleConfirm() throws IOException, TimeoutException, InterruptedException {
        Channel channel = ConnectionUtil.getChannel();

        // 开启发布确认
        channel.confirmSelect();

        channel.queueDeclare(Queue_name, false, false, false, null);


        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < sendCount; i++) {

            String message = "消息" + i;
            System.out.println("生产者发送消息：" + message);
            channel.basicPublish("", Queue_name, null, message.getBytes());

            // 获取消息发送到RabbitMQ的状态
            boolean b = channel.waitForConfirms();
            if(b){
                System.out.println(message + "：发送成功！");
            }
        }
        System.out.println("单个消息确认，100条消息耗时：" + (System.currentTimeMillis() - startTime));

    }


    /**
     * 批量确认
     */
    public static void batchConfirm() throws IOException, TimeoutException, InterruptedException {
        Channel channel = ConnectionUtil.getChannel();

        // 开启发布确认
        channel.confirmSelect();

        channel.queueDeclare(Queue_name, false, false, false, null);


        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < sendCount; i++) {

            String message = "消息" + i;
            System.out.println("生产者发送消息：" + message);
            channel.basicPublish("", Queue_name, null, message.getBytes());

            if(i%batchConfirmCount == 0){
                // 获取消息发送到RabbitMQ的状态
                boolean b = channel.waitForConfirms();
                if(b){
                    System.out.println(message + "：发送成功！");
                }
            }

        }
        System.out.println("批量消息确认，100条消息耗时：" + (System.currentTimeMillis() - startTime));

    }


    /**
     * 异步确认
     */
    public static void syncConfirm() throws IOException, TimeoutException, InterruptedException {
        // 声明一个map
        ConcurrentSkipListMap<Long, String> concurrentSkipListMap = new ConcurrentSkipListMap();

        Channel channel = ConnectionUtil.getChannel();

        // 开启发布确认
        channel.confirmSelect();

        channel.queueDeclare(Queue_name, false, false, false, null);


        /**
         * 确认收到消息回调接口
         * 参数：
         * 1.消息序列号
         * 2.true:批量确认序列号小于等当前序列号的消息。  false:只确认当前消息。
         */
        ConfirmCallback ackConfirmCallback = new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    ConcurrentNavigableMap<Long, String> confirmHead = concurrentSkipListMap.headMap(deliveryTag, true);
                    confirmHead.clear();
                } else {
                    concurrentSkipListMap.remove(deliveryTag);
                }
                System.out.println("确认收到消息：deliveryTag = " + deliveryTag + "，multiple = " + multiple );
            }
        };


        /**
         * 未收到消息回调接口
         * 参数：
         * 1.消息序列号
         * 2.true:批量确认序列号小于等当前序列号的消息。  false:只确认当前消息。
         */
        ConfirmCallback notConfirmCallback = new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    ConcurrentNavigableMap<Long, String> confirmHead = concurrentSkipListMap.headMap(deliveryTag, true);
                    confirmHead.clear();
                } else {
                    concurrentSkipListMap.remove(deliveryTag);
                }
                System.out.println("未收到消息：deliveryTag = " + deliveryTag + "，multiple = " + multiple);
            }
        };

        // 添加一个异步确认的监听器
        channel.addConfirmListener(ackConfirmCallback, notConfirmCallback);

        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < sendCount; i++) {
            String message = "消息" + i;
            System.out.println("生产者发送消息：" + message);

            // 记录发送的消息
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(), message);

            channel.basicPublish("", Queue_name, null, message.getBytes());
        }
        System.out.println("批量消息确认，100条消息耗时：" + (System.currentTimeMillis() - startTime));

    }


}
