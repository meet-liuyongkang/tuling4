package com.tuling.jvm.classloader;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/2/22 4:50 下午
 */
public class User1 {

    static {
        System.out.println("User1 已经被加载，加载器:" + User1.class.getClassLoader());
    }

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
