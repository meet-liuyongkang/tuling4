package com.tuling.jvm.classloader;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 测试类的懒加载
 * @date 2022/2/21 7:54 下午
 *
 * 打印内容：
 *      加载了主类
 *      加载了TestA
 *      初始化了TestA
 *
 */
public class TestLazyClassLoader {

    static {
        System.out.println("加载了主类");
    }

    public static void main(String[] args) {
        new TestA();
    }

}
