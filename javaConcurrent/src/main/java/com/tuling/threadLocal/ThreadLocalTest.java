package com.tuling.threadLocal;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 当我们定义一个变量，需要在项目的任何地方都可以使用的时候，就可以考虑使用ThreadLocal。
 * ThreadLocal 是一个线程本地变量，在当前线程的任何地方都可以获取到，线程之间相互隔离，互不影响。
 *
 * 源码解析：查看set()、get()源码可以发现，每个线程自己维护了一个ThreadLocal.ThreadLocalMap变量。
 * ThreadLocalMap对象中又维护了一个private Entry[] table;变量。这个Entry是定义ThreadLocal的弱引用类型，其中维护了一个value变量。
 * Entry有点类似map，是k-v形式的数据结构，只不过k是ThreadLocal对象，value才是正常存储的值。也就是说，本地线程变量的值其实是存储在线程中的。
 * 这样的好处是。当线程结束后，线程内部维护的本地变量自动被回收。
 * 使用弱引用的原因是，在我们目前的程序中，一般都是使用线程池，线程池中的Thread是循环使用的，它并不会消亡。我们对线程池中的线程设置一个ThreadLocal时，
 * 如果是强引用，那么即时在ThreadLocal变量使用完之后，将其设置为null，由于Entry还持有ThreadLocal对象的引用，这个对象将永远不会被垃圾回收器回收。
 *
 * @date 2022/4/8 2:45 下午
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void main(String[] args) {
        threadLocal.set("aaa");
        testA();

        new Thread(new Runnable() {
            @Override
            public void run() {
                testA();
            }
        }).start();
    }

    public static void testA(){
        System.out.println("线程名称：" +Thread.currentThread().getName() +", 获取到的值为：" + threadLocal.get());
    }

}
