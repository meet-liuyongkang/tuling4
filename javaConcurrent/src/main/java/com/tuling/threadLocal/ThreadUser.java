package com.tuling.threadLocal;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/8 4:18 下午
 */
public class ThreadUser {

    public ThreadUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ThreadUser() {
    }

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ThreadUser{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
