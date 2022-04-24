package com.tuling.base.work;

import com.rabbitmq.client.Channel;
import com.tuling.base.utils.ConnectionUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 工作队列模式-生产者
 * 工作队列模式，相比于简单模式，就是多了几个消费者，当生产者生产消息时，由一个或多个消费者消费。
 * 特点：
 *      1. 在一个队列中如果有多个消费者，那么消费者之间对于同一个消息的关系是竞争的关系。默认策略是消息平均分配到不同的消费者上。
 *      2. Work Queues 对于任务过重或任务较多情况使用工作队列可以提高任务处理的速度。例如：短信服务部署多个，只需要有一个节点成功发送即可。
 * @date 2022/4/19 3:53 下午
 */
public class WorkProducer {

    private static String QUEUE_NAME = "WorkQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, true, null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();

            channel.basicPublish("", QUEUE_NAME, null, next.getBytes());
        }

    }

}
