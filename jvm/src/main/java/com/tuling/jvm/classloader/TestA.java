package com.tuling.jvm.classloader;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/2/22 3:48 下午
 */
public class TestA {
    static {
        System.out.println("加载了TestA");
    }

    public TestA() {
        System.out.println("初始化了TestA");
    }

}
