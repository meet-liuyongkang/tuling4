package com.tuling.spring.thread;

import org.springframework.core.task.TaskDecorator;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/4/8 5:16 下午
 */
public class TaskDecoratorTest implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {

        //主流程
        String res = MetaUserContext.getUserName();
        System.out.println("线程名称："+ Thread.currentThread().getName() +"，装饰前的值：" + res);
        //子线程逻辑
        return () -> {
            try {
                //将变量重新放入到run线程中。
                MetaUserContext.setUserName(res);
                runnable.run();
            } finally {
                MetaUserContext.remove();
            }
        };
    }
}
