package com.tuling.algorithm.sort.secret;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 *
 * 对称加密算法
 * 优点：算法对消息双方公开、计算量小、加密速度快、加密效率高。
 * 缺点：在数据传送前，发送方和接收方必须商定好秘钥，然后双方保存好秘钥。如果一方的秘钥被泄露，那么加密信息就会被破解。
 *
 *
 * DES加密介绍 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 * 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 * 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现 。
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 * @author liuyongkang
 */
public class DesTest {

    public static void main(String[] args) {
        //待加密内容
        String targetStr = "abcqwe";
        //密码，长度必须是8的倍数
        String password = "12345678";

        byte[] encrypt = encrypt(targetStr.getBytes(), password);
        System.out.println("明文：" + targetStr);
        System.out.println("被加密为：" + new String(encrypt));

        System.out.println();

        byte[] decrypt = decrypt(encrypt, password);
        System.out.println("密文：" + new String(encrypt));
        System.out.println("被解密为：" + new String(decrypt));

    }


    /**
     * 加密方法
     * @param target 需要加密的明文
     * @param password DES加密的密码
     * @return
     */
    public static byte[] encrypt(byte[] target, String password){
        try {
            //DES加密要求有一个信任的随机数源
            SecureRandom secureRandom = new SecureRandom();

            //获取DES工厂实例
            SecretKeyFactory desFactory = SecretKeyFactory.getInstance("DES");
            //定义 DES 密钥长度的常量（以字节为单位）
            DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
            //获取秘钥key
            SecretKey secretKey = desFactory.generateSecret(desKeySpec);

            //获取DES加密的cipher对象
            Cipher cipher = Cipher.getInstance("DES");
            //初始化cipher对象，Cipher.ENCRYPT_MODE 表示加密
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
            //实际由cipher对象来完成加密
            return cipher.doFinal(target);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密方法
     * @param target
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] target, String password){
        try {
            //创建一个随机数源
            SecureRandom secureRandom = new SecureRandom();

            //获取DES秘钥的Key工厂实例
            SecretKeyFactory desSecretKeyFactory = SecretKeyFactory.getInstance("DES");

            //根据密码获取key的规格
            DESKeySpec desSecretKey = new DESKeySpec(password.getBytes());
            //根据DES key的规格获取SecretKey
            SecretKey secretKey = desSecretKeyFactory.generateSecret(desSecretKey);

            //获取DES加密对象的实例
            Cipher cipher = Cipher.getInstance("DES");
            //根据需要的参数初始化加密对象, Cipher.DECRYPT_MODE 表示解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey, secureRandom);

            //完成解密操作
            return cipher.doFinal(target);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
