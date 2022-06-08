package com.tuling.algorithm.sort.secret;

import java.security.MessageDigest;

/**
 * @author liuyongkang
 * @date Create in 2022/6/8 14:56
 * @desc    SHA加密算法是不可逆的算法，是对明文进行摘要
 *
 * 不可逆加密算法：
 * 优点：不可逆、易计算、特征化
 * 缺点：可能存在散列冲突
 *
 *
 * 对于长度小于2^64位的消息，SHA1会产生一个160位(40个字符)的消息摘要。当接收到消息的时候，这个消息摘要可以用来验证数据的完整性。在传输的过程中，数据很可能会发生变化，那么这时候就会产生不同的消息摘要。
 *
 * SHA1有如下特性：
 * 不可以从消息摘要中复原信息；
 * 两个不同的消息不会产生同样的消息摘要,(但会有1x10 ^ 48分之一的机率出现相同的消息摘要,一般使用时忽略)。
 *
 */
public class SHATest {

    public static void main(String[] args) throws Exception {
        String content = "SHA加密算法测试内容";

        MessageDigest shaDigest = MessageDigest.getInstance("SHA");
        byte[] shaResult = shaDigest.digest(content.getBytes());
        printDigestBytes(shaResult);

        MessageDigest sha1Digest = MessageDigest.getInstance("SHA1");
        byte[] sha1Result = sha1Digest.digest(content.getBytes());
        printDigestBytes(sha1Result);

        MessageDigest sha224Digest = MessageDigest.getInstance("SHA-224");
        byte[] sha224Result = sha224Digest.digest(content.getBytes());
        printDigestBytes(sha224Result);

        MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
        byte[] sha256Result = sha256Digest.digest(content.getBytes());
        printDigestBytes(sha256Result);

        MessageDigest sha384Digest = MessageDigest.getInstance("SHA-384");
        byte[] sha384Result = sha384Digest.digest(content.getBytes());
        printDigestBytes(sha384Result);

        MessageDigest sha512Digest = MessageDigest.getInstance("SHA-512");
        byte[] sha512Result = sha512Digest.digest(content.getBytes());
        printDigestBytes(sha512Result);
    }

    private static void printDigestBytes(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb);
    }

}
