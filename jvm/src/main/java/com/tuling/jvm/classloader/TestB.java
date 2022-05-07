package com.tuling.jvm.classloader;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/2/22 3:48 下午
 */
public class TestB {
    static {
        System.out.println("加载了TestB");
    }

    public TestB() {
        System.out.println("初始化了TestB");
    }
}
