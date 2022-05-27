package com.tuling.juc;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 *
 * 锁标致信息存放在对象头中，由对象头的最后的三位来表示。
 * 对象头：由32位组成。
 *
 *
 * jdk1.6 之后对synchronize做了许多优化：
 * 1.锁的膨胀升级：
 *      a.无锁：该对象没有被synchronize修饰，即为无锁，标识位：001
 *              当JVM参数关闭偏向锁后，创建的对象默认是无锁态。经过一次锁后直接升级为轻量锁。
 *              对象头：高25位hashcode，紧随4位GC分代年龄，后1位偏向锁标识，最后2位锁标识。

 *      b.偏向锁：只有同一个线程获取该锁时，即为偏向锁，标识位：101
 *              当JVM开启偏向锁后，默认创建的对象就是准备偏向状态，此时锁是偏向锁，但是偏向的ThreadId为空。最后三位101。
 *              当此对象经过第一次锁定后，将ThreadId设置为获取锁的线程Id，当其他线程获取了该锁时，就会升级为轻量锁。
 *              对象头：高23位偏向线程id，后2位Epoch记录偏向时间，后4位GC分代年龄，后1位偏向标识，后2位锁标识。
 *
 *      c.轻量锁：多个线程交替获取该锁，但是锁的竞争并不激烈。例如多个线程交替使用锁，没有竞争的情况，
 *              或者线程执行很快，竞争锁时，只需要等待很短的时间（短暂的自旋）就能获取锁，。锁标识位：000
 *              当偏向锁被第二个线程获取时，就是将偏向锁升级为轻量锁。
 *              对象头：高25位记录一个指针，这个指针内存中包含了hashcode，GC分代年龄等信息。
 *
 *
 *
 *      d.重量锁：当轻量锁的竞争变得激烈时，其他线程在短暂的自旋后，还是无法获取到锁时，轻量锁就会升级为重量锁。标识为010
 *              对象头：高25位记录一个指针，这个指针内存中包含了hashcode，GC分代年龄等信息。
 *
 *      e.被回收的对象：锁标识位：011
 *
 *
 * @date 2022/5/10 10:52 上午
 */
public class SynchronizeTest {




}
