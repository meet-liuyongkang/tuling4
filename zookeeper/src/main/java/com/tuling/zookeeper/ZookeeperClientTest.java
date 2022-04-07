package com.tuling.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/3/31 4:50 下午
 */
public class ZookeeperClientTest {


    /**
     * 1.Zookeeper的节点信息，格式为 ip1:port ，多个节点由因为逗号隔开
     * 2.还可以在连接上指定该客户端连接的空间，例如 ip1:port1,ip2:port2,ip3:port3/my_node
     *   指定客户端的连接空间后，该客户端 的所有操作都只能在这个空间下进行，创建/sub_node，会在/my_node 下建一个/sub_node
     *
     * 3.创建Zookeeper对象参数说明
     *     connectString：ZK服务端地址
     *     sessionTimeout：session超时时间
     *     watcher：事件通知处理器
     *     canBeReadOnly：是否只读
     *     sessionId：会话id，连接建立后，调用getSessionId()方法获取。
     *     sionPasswd：会话秘钥，连接建立后，调用getSessionPasswd()方法获取。
     *          最后这两个参数可以唯一标识一个session，可以通过这两个参数恢复会话。
     *
     */
    private static final String zkNodes = "192.168.254.111:2181";

    /**
     * session超时时间，单位是毫秒，超过这个时间没有接收到客户端的心跳，会话就会失效。
     */
    private static final int sessionTimeout = 5000;

    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;
        try {
            //创建连接
            zooKeeper = new ZooKeeper(zkNodes, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState() == Event.KeeperState.SyncConnected && watchedEvent.getType() == Event.EventType.None){
                        System.out.println("连接成功！");
                    }

                }
            });

            //同步创建节点
//            createCatalogue(zooKeeper);

            //异步创建节点
//            syncCreateCatalogue(zooKeeper);

            //获取和修改数据
//            getData(zooKeeper);



        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (zooKeeper != null){
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 同步创建节点
     * @param zooKeeper
     */
    public static void createCatalogue(ZooKeeper zooKeeper){
        //同步创建节点
        byte[] data = "javaClientTest".getBytes();
        try {
            zooKeeper.create("/zookeeper/JavaClient/java01", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("同步创建节点成功");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void syncCreateCatalogue(ZooKeeper zooKeeper){
        //异步创建节点
        byte[] data2 = "javaClientTestSync".getBytes();
        zooKeeper.create("/zookeeper/JavaClient/java02", data2, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.Create2Callback() {
            @Override
            public void processResult(int i, String s, Object o, String s1, Stat stat) {
                System.out.println();
            }
        }, "context");
        System.out.println("异步创建节点成功");
    }

    public static void getData(ZooKeeper zooKeeper) {
        String path = "/zookeeper/JavaClient/java";

        try {

            //查询数据
            Stat stat = new Stat();
            byte[] data = zooKeeper.getData(path, false, stat);
            System.out.println("当前数据版本：" + stat.getVersion() + "  \n 最后修改时间：" + stat.getMtime());

            System.out.println("修改前的数据" + new String(data));

            //修改数据
            zooKeeper.setData(path, "modify after data 002".getBytes(), stat.getVersion());
            byte[] modifyData = zooKeeper.getData(path, false, stat);
            System.out.println("修改后的数据" + new String(modifyData));


        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
