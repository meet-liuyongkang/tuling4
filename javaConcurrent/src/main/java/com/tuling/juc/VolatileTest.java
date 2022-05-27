package com.tuling.juc;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description volatile 保证了 可见性 和 有序性
 * @date 2022/5/7 10:14 上午
 */
public class VolatileTest {

    public volatile static Boolean flag = true;

    public static int count = 0;

    public static int a,b,x,y;


    public static void main(String[] args) throws InterruptedException {
//        shortWait();
//        test3();
        test4();
    }

    /**
     * 测试可见性
     */
    @SuppressWarnings("all")
    public static void test1(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1开始执行");

                // 1.while中如果为空，那么flag被线程2修改后，线程1基本上不会感知到。
                // 2.while中如果执行打印 System.out.println("a"); flag被修改后线程1可以感知到。
                // 3.while中如果执行count++;  count为int类型时，flag的修改不会感知到。
                // 4.while中如果执行count++;  count为Integer类型时，flag的修改可以感知到。
                // 上面的四种现象暂时无法解释，但是推测是由于 flag加载到CPU的缓存和寄存器中，后续没有其他线程操作任何数据，
                //那么这个flag在CPU中的值不会被清理，如果执行 count++;由于CPU加载了其他数据到内存，在计算时，如果内存不够了，
                //就会将之前加载的flag移除，下次用的时候再加载，就加载到了线程2修改后的数据，所以循环停止。
                while (flag){
                    System.out.println();
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2开始执行");
                flag = false;
            }
        }).start();

    }


    /**
     * 指令重排
     * JVM和CPU为了让指令执行的更加高效，都会对我们的代码指令进行优化，这时就可能出现指令重排的问题。
     */
    public void test2(){
        int a = 1;

        int b = 1;
        int c = 1;

        int x = a;

        // 上面的几行代码，在CPU看来，第一行处理了数据a，这个时候后面两行代码和a，没有关系，但是第四行代码和a有关系，那么为了避免后面
        // 再次从内存读取a的性能消耗，操作系统会把 int x = a; 放在 int a = 1;下面执行，这样更加高效。
        // 这个就是指令重排，指令重排的前提是，指令交换前后不能影响最终的计算结果。
    }

    /**
     * 指令重排与多线程
     */
    @SuppressWarnings("all")
    public static void test3() throws InterruptedException {
        while (true){
            a = 0;
            b = 0;
            x = 0;
            y = 0;

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    //线程1等待 40000 纳秒，目的是更好的让两个线程同时执行。
                    shortWait();
                    a = 1;
                    x = b;
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            //输出预测：a=1,x=0,b=1,y=1  输出：x=0, y=1
            //输出预测：a=1,b=1,x=1,y=1  输出：x=1, y=1
            //输出预测：b=1,y=0,a=1,x=1  输出：x=1, y=1

            //只有发生指令重排，才可能出现，x=0,y=0
            if(x == 0 && y == 0){
                return;
            }
            count++;
            System.out.println("第" + count + "次循环，x=" + x + "，y="+y);
        }
    }

    public static void shortWait(){
        long nanoTime = System.nanoTime();
        do {
        }while (System.nanoTime() - nanoTime < 40000);
    }


    /**
     * 测试原子性
     */
    public static void test4(){
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        count++;
                    }
                }
            }).start();
        }

        System.out.println(count);
    }

}
