package com.tuling.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 线程池中的子线程，在运行时继承父线程的变量，在运行完后，会清除继承自父线程的变量，同时恢复本身原有的变量。
 *
 * 源码：
 *
 * @date 2022/4/8 4:24 下午
 */
public class TransmittableThreadLocalTest2 {

    /**
     * 经过ttl
     */
    private static ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));

    private static TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal();


    public static void main(String[] args) throws InterruptedException {

        //先在子线程中设置变量
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    transmittableThreadLocal.set("bbb");
                    testA();
                }
            });
        }

        Thread.sleep(5000);

        //然后单独在子线程中获取一下变量，这个时候应该是获取不到的，因为子线程用完后，会清空不是原有的变量
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                testA();
            }
        });

        //主线程也获取不到
        testA();

        Thread.sleep(5000);

        //在主线程中设置变量，然后在子线程中修改
        transmittableThreadLocal.set("aaa");

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    transmittableThreadLocal.set("bbb");
                    testA();
                }
            });
        }

        testA();

        Thread.sleep(5000);

        //重新拿一次变量，发现并没有受到上面子线程修改的影响
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    testA();
                }
            });
        }

    }


    public static void testA(){
        System.out.println("线程名称：" +Thread.currentThread().getName() +", 获取到的值为：" + transmittableThreadLocal.get());
    }

}
