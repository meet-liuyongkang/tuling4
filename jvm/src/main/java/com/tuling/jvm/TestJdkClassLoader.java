package com.tuling.jvm;

import com.sun.crypto.provider.DESKeyFactory;
import sun.misc.Launcher;

import java.net.URL;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/2/21 8:44 下午
 */
public class TestJdkClassLoader {

    public static void main(String[] args) {
        //引导类加载器（由于引导类加载器是C语言写的，所以这里通过Java代码获取到的对象是null）
        System.out.println(String.class.getClassLoader());

        //扩展类加载器
        System.out.println(DESKeyFactory.class.getClassLoader());

        //应用程序类加载器
        System.out.println(TestJdkClassLoader.class.getClassLoader());


        System.out.println();
        System.out.println();


        //系统的类加载器，其实就是应用程序类加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(appClassLoader);

        ClassLoader extClassLoader = appClassLoader.getParent();
        System.out.println(extClassLoader);

        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);


        System.out.println();
        System.out.println();


        System.out.println("引导类加载器的路径");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url);
        }

        System.out.println();

        System.out.println("扩展类加载器的路径");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println();

        System.out.println("应用程序类加载器的路径");
        System.out.println(System.getProperty("java.class.path"));

    }

}
