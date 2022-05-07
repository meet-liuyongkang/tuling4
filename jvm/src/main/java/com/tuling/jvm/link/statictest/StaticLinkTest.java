package com.tuling.jvm.link.statictest;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description     静态链接
 *
 * 一个类A引用另外一个类B，在编译.class文件时，并不能确定引用的类B的具体内存地址，所以这个时候会用一个引用变量来描述引用的类B
 * （这个变量被称为符号引用），在类加载的解析阶段，会将引用类型替换成类B在方法区中的内存地址（这个引用地址被称为直接引用），
 * 这个替换的过程被称为静态链接。
 *
 * @date 2022/5/5 2:50 下午
 */
public class StaticLinkTest {
    static class Human {

    }
    static class Man extends Human {

    }
    static class Woman extends Human {

    }

    public void sayHello(Human man) {
        System.out.println("我是human");
    }

    public void sayHello(Man man) {
        System.out.println("我是man");
    }

    public void sayHello(Woman man) {
        System.out.println("我是woman");
    }


    public static void main(String[] args) {
        // 这里 Human 被称作 静态类型 or 外观类型，Man 被称为实际类型。
        // 静态类型在编译期就确定了，并且不会改变。而实际类型是在使用时才确定的。
        Human man = new Man();
        Human woman = new Woman();
        StaticLinkTest staticLinkTest = new StaticLinkTest();
        staticLinkTest.sayHello(man);
        staticLinkTest.sayHello(woman);
    }

}
