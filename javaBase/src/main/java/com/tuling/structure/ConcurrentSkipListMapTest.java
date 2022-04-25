package com.tuling.structure;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description  线程安全的跳表Map
 *
 * 说明：跳表的本质是同时维护多张链表，最底层的链表包含完整数据，其上的每一层都是索引，类似于MySQL的结构。
 *
 * 对于单链表，即使链表是有序的，如果想要在其中查找某个数据，也只能从头到尾遍历链表，这样效率自然就会很低。
 * 跳表就不一样了，跳表是一种可以用来快速查找的数据结构，有点类似于平衡树。它们都可以对元素进行快速的查找。
 * 但一个重要的区别是：对平衡树的插入和删除往往很可能导致平衡树进行一次全局的调整；而对跳表的插入和删除，
 * 只需要对整个数据结构的局部进行操作即可。这样带来的好处是：在高并发的情况下，需要一个全局锁，来保证整个平衡树的线程安全；
 * 而对于跳表，则只需要部分锁即可。这样，在高并发环境下，就可以拥有更好的性能。就查询的性能而言，跳表的时间复杂度是 O(logn)，
 * 所以在并发数据结构中，JDK 使用跳表来实现一个 Map。
 *
 * 跳表的本质，是同时维护了多个链表，并且链表是分层的。
 *
 * 特点：
 * 1. 查询的时间复杂度为log(n)
 * 2. 跳表中所有的key都是有序的
 * 3. ConcurrentSkipListMap 的并发度非常高，并发越高，ConcurrentSkipListMap 的优势越明显。
 *
 * @date 2022/4/25 4:55 下午
 */
public class ConcurrentSkipListMapTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> concurrentSkipListMap = new ConcurrentSkipListMap();
        concurrentSkipListMap.put(3, "333");
        concurrentSkipListMap.put(1, "111");
        concurrentSkipListMap.put(4, "444");
        concurrentSkipListMap.put(2, "222");
        concurrentSkipListMap.put(5, "555");


        // key有序
        for (Map.Entry<Integer, String> integerStringEntry : concurrentSkipListMap.entrySet()) {
            System.out.println("key = " + integerStringEntry.getKey() + "  ,value = " + integerStringEntry.getValue());
        }

        System.out.println();

        // headMap 返回小于当前key的map
        ConcurrentNavigableMap<Integer, String> integerStringConcurrentNavigableMap = concurrentSkipListMap.headMap(3);
        for (Map.Entry<Integer, String> integerStringEntry : integerStringConcurrentNavigableMap.entrySet()) {
            System.out.println("key = " + integerStringEntry.getKey() + "  ,value = " + integerStringEntry.getValue());
        }


    }

}
