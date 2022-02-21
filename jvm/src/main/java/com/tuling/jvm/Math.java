package com.tuling.jvm;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 *
 * 1.当通过Java命令运行一个程序的main函数来启动程序时，首先会通过 类加载器 main方法所在的类加载到JVM
 * 2.类加载的过程：
 *      加载 -> 验证 -> 准备 -> 解析 -> 初始化
 *      -> 使用 -> 卸载 (这两个不是)
 * 3.注意，类加载是懒加载机制，只有在使用的时候才会加载，而不是启动项目的时候加载所有
 *
 * @date 2022/2/21 6:02 下午
 */
public class Math {

    public static void main(String[] args) {
        System.out.println("aaa");
    }

}
