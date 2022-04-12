package com.tuling.threadLocal;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 我们在程序中难免会使用多线程的情况，有时候我们想要将父线程的ThreadLocal变量传递给子线程，就可以使用InheritableThreadLocal。
 * InheritableThreadLocal 是jdk提供的一个类，主要是用于在创建的时候，将父线程的本地变量传递给子线程。
 *
 * 源码解析：
 * InheritableThreadLocal继承ThreadLocal。
 * 查看源码可知，在创建一个线程对象的时候，在Thread的构造函数中会调用自身的init方法，init方法中会先获取到父线程的对象，
 * 判断父线程中的inheritableThreadLocals变量不为null，则将父线程的inheritableThreadLocals变量赋值给子线程，从这里可以看出，
 * 父子线程中的inheritableThreadLocals其实都是指向同一个对象，所以无论是在父线程还是子线程中修改了该对象，那么父子线程中都会生效。
 *
 * 注意点：只有创建的时候会传递，所以线程池的情况下，线程池中复用已经创建好的线程对象是不会复制父线程的本地变量。
 *
 * @date 2022/4/8 2:55 下午
 */
public class InheritableThreadLocalTest {

    private static InheritableThreadLocal<ThreadUser> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ThreadUser threadUser = new ThreadUser();
        threadUser.setName("aaa");
        threadUser.setAge(111);
        inheritableThreadLocal.set(threadUser);
        testA();

        new Thread(new Runnable() {
            @Override
            public void run() {
                testA();

                //子线程修改值
                ThreadUser subThreadUser = inheritableThreadLocal.get();
                subThreadUser.setName("bbb");
                subThreadUser.setAge(222);
            }
        }).start();

        Thread.sleep(2 * 1000);

        //主线程再次获取本地变量
        testA();
    }

    public static void testA(){
        System.out.println("线程名称：" +Thread.currentThread().getName() +", 获取到的值为：" + inheritableThreadLocal.get().toString());
    }

}
