package com.tuling.zookeeper;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:jiangyue@dtstack.com">江月 At 袋鼠云</a>.
 * @description
 * @date 2022/3/24 8:51 下午
 */
public class DigestUtils {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //user1:+7K83PhyQ3ijGj0ADmljf0quVwQ=
        String sId = DigestAuthenticationProvider.generateDigest("user1:pass1");
        System.out.println(sId);
    }

}
