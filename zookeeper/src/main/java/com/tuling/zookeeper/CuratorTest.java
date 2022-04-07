package com.tuling.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description Curator是对Zookeeper高都封装的框架。
 * 在会话重新连接、Watch 反复注册、多种异常处理等使用场景中，用原生的 ZooKeeper 处理比较复杂。
 * 而在使用 Curator 时，由于其对这些功能都做了高度的封装，使用起来更加简单，不但减少了开发时间，而且增强了程序的可靠性
 * @date 2022/3/31 7:56 下午
 */
public class CuratorTest {

    private static final String zkNodes = "192.168.254.111:2181";

    public static void main(String[] args) {
        //初始睡眠时间
        int baseSleepTimeMs = 1000;

        //重新次数
        int maxRetries = 3;

        //创建一个重试策略，当客户端异常退出或者与服务器失去连接时，会进行重试。
        //重试一组次数，重试之间的睡眠时间增加
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        //只重试一次
//        RetryOneTime retryOneTime = new RetryOneTime(baseSleepTimeMs);
        //重试最大次数
//        RetryNTimes retryNTimes = new RetryNTimes(2, baseSleepTimeMs);
        //在给定的时间之前进行重试
//        RetryUntilElapsed retryUntilElapsed = new RetryUntilElapsed(9000, baseSleepTimeMs);


        //通过工厂类创建一个连接对象
        CuratorFramework curatorClient = CuratorFrameworkFactory.builder()
                //服务器地址列表
                .connectString(zkNodes)
                //session超时时间
                .sessionTimeoutMs(5000)
                //连接超时时间
                .connectionTimeoutMs(5000)
                //重试策略
                .retryPolicy(retryPolicy)
                //指定客户端的工作目录，所有的操作，都会默认在前面加上这个路径
                .namespace("zookeeper/JavaClient/curatorClient")
                .build();

        curatorClient.start();

        String path = "/java001";
        try {
            //创建节点
//            curatorClient.create().forPath(path, "curatorTestData".getBytes());

            //修改数据
            curatorClient.setData().forPath(path, "newData".getBytes());

            //获取数据
            byte[] dataBytes = curatorClient.getData().forPath(path);
            System.out.println(new String(dataBytes));


            //删除节点
//            curatorClient.delete().forPath(path);

            //保障删除，只要客户端连接有效，就会在后台持续发送请求，直到这个节点删除成功
//            curatorClient.delete().guaranteed().forPath(path);

            //递归删除，如果存在子节点，则会递归将子节点，子节点的子节点...全部删除
//            curatorClient.delete().deletingChildrenIfNeeded().forPath(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
