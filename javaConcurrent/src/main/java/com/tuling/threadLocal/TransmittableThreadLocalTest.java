package com.tuling.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description TransmittableThreadLocal是阿里开源的一个jar包中的一个类，此类演示基本使用。
 *
 * 源码：
 *
 * @date 2022/4/8 4:24 下午
 */
public class TransmittableThreadLocalTest {

    private static ExecutorService executorServiceOne = Executors.newFixedThreadPool(2);

    /**
     * 经过ttl
     */
    private static ExecutorService executorServiceTwo = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));

    private static TransmittableThreadLocal<ThreadUser> transmittableThreadLocal = new TransmittableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        transmittableThreadLocal.set(new ThreadUser("aaa", 111));
        testA();

        for (int i = 0; i < 5; i++) {
            transmittableThreadLocal.set(new ThreadUser("aaa"+i, i));

            //线程池一
            executorServiceOne.submit(new Runnable() {
                @Override
                public void run() {
                    testA();
                }
            });
        }

        Thread.sleep(3000);
        System.out.println();
        System.out.println();

        for (int i = 0; i < 5; i++) {
            transmittableThreadLocal.set(new ThreadUser("aaa"+i, i));

            //线程池二
            executorServiceTwo.submit(new Runnable() {
                @Override
                public void run() {
                    testA();
                }
            });
        }


    }


    public static void testA(){
        System.out.println("线程名称：" +Thread.currentThread().getName() +", 获取到的值为：" + transmittableThreadLocal.get().toString());
    }

}
