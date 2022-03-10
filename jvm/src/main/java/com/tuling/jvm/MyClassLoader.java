package com.tuling.jvm;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description 自定义的类加载器
 * @date 2022/2/22 4:28 下午
 */
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> user1 = myClassLoader.loadClass("/Users/liuyongkang/IdeaProjects/tuling4/ExtClassResource/com/tuling/jvm/User2.class", false);
//        Class<?> user1 = myClassLoader.loadClass("User2", false);
        System.out.println(user1.getClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        //首先，检查类是否已经加载过
        // First, check if the class has already been loaded
        Class<?> c = findLoadedClass(name);
            //没有加载过
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    //如果有父加载器，则由父加载器加载
                    if (getParent() != null) {
                        c = getParent().loadClass(name);
                    } else {
                        //如果没有父加载器，则自己加载
                        c = findLoadedClass(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
        return c;
    }


    /**
     *
     */
    private byte[] loadByte(String name) throws IOException {
        if(name.lastIndexOf(".class") > 1){
            name = name.replace(".class", "");
        }
        String filePath = name.replace(".", "\\");
        filePath = filePath + ".class";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        int len = fileInputStream.available();
        byte[] b = new byte[len];
        fileInputStream.read(b);
        return b;
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        try {
            byte[] bytes = loadByte(name);
            return defineClass("com.tuling.jvm.User2", bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }

}
