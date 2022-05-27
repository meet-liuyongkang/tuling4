package com.tuling.structure.arithmetic;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/5/13 10:06 上午
 */
public class ArithmeticTest {

    Integer age;

    public ArithmeticTest(Integer age){
        this.age = age;
    }

    public static void main(String[] args) {
        String d = "aaa";
        fun(d);
        System.out.println(d);


        char[] chars = new char[]{'a','c'};
        fun2(chars);
        System.out.println(chars);


        ArithmeticTest arithmeticTest = new ArithmeticTest(18);
        arithmeticTest.fun3(arithmeticTest);
        System.out.println(arithmeticTest.age);
    }

    public static void fun(String d){
        d = "bbb";
        System.out.println(d);
    }

    public static void fun2(char[] chars){
        chars = new char[]{'f'};
        chars[0] = 'g';
    }

    public void fun3(ArithmeticTest test){
        test = new ArithmeticTest(33);
        test.setAge(44);
    }


    public void setAge(Integer age) {
        this.age = age;
    }
}
