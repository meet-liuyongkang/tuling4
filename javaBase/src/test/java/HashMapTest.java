import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/5/17 2:58 下午
 */
public class HashMapTest {

    @Test
    public void test1(){
        Map<Integer,Integer> hashMap1 = new HashMap<>();
        hashMap1.put(1,1);
        hashMap1.put(2,2);

        Map<Integer,Integer> hashMap2 = new HashMap<>();
        hashMap1.put(2,2);
        hashMap1.put(1,1);

        System.out.println(hashMap1.equals(hashMap2));



        Map<Integer,Integer> treeMap1 = new TreeMap<>();
        hashMap1.put(1,1);
        hashMap1.put(2,2);

        Map<Integer,Integer> treeMap2 = new TreeMap<>();
        hashMap1.put(2,2);
        hashMap1.put(1,1);

        System.out.println(treeMap1.equals(treeMap2));


    }

}
