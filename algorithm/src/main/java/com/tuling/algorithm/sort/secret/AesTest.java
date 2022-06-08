package com.tuling.algorithm.sort.secret;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author liuyongkang
 * @date Create in 2022/6/8 09:33
 * @desc
 *
 * 对称加密算法
 * 优点：算法对消息双方公开、计算量小、加密速度快、加密效率高。
 * 缺点：在数据传送前，发送方和接收方必须商定好秘钥，然后双方保存好秘钥。如果一方的秘钥被泄露，那么加密信息就会被破解。
 *
 */
public class AesTest {

    /**
     * 加密算法名称 AES
     */
    private static final String ALGORITHM_NAME = "AES";

    /**
     * 加密因子，可根据您的需要自定义
     */
    private static final String DEFAULT_ENCRYPT_RULE = "AES/CBC/PKCS5Padding";
    private static final String RANDOM_KEY_ALGORITHM = "SHA1PRNG";
    private static final String RANDOM_KEY_ALGORITHM_PROVIDER = "SUN";


    public static void main(String[] args) {
        String cotent = "这是随便写的一个明文";
        String decrypt = encrypt(cotent);
        System.out.println("加密后：" + decrypt);
        String encrypt = decrypt(decrypt);
        System.out.println("解密后：" + encrypt);
    }

    /**
     * AES加密
     * @param content 明文
     * @return  密文
     */
    public static String encrypt(String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }

        try {
            byte[] bytes = getCipher(true).doFinal(content.getBytes());
            return new String(Base64.getEncoder().encode(bytes));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     * @param content 密文
     * @return  明文
     */
    public static String decrypt(String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }

        try {
            byte[] bytes = getCipher(false).doFinal(Base64.getDecoder().decode(content.getBytes()));
            return new String(bytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取加密对象
     * @param isEncrypted 是否获取加密对象，true=加密，false=解密
     * @return
     */
    public static Cipher getCipher(boolean isEncrypted){
        Cipher cipher = null;
        try {
            //获取Key构造器
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);

            //key构造器初始化需要一个信任的随机数源
            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_KEY_ALGORITHM, RANDOM_KEY_ALGORITHM_PROVIDER);
            //设置随机数源的种子
            secureRandom.setSeed(DEFAULT_ENCRYPT_RULE.getBytes());

            //初始化key构造器
            keyGenerator.init(128, secureRandom);
            //获取原始秘钥
            SecretKey originalKey = keyGenerator.generateKey();

            //根据原始秘钥，获取需要的算法秘钥
            SecretKey secretKey = new SecretKeySpec(originalKey.getEncoded(), ALGORITHM_NAME);

            //获取具体算法的加密对象
            cipher = Cipher.getInstance(ALGORITHM_NAME);

            int encryptMode = Cipher.ENCRYPT_MODE;
            if (!isEncrypted) {
                encryptMode = Cipher.DECRYPT_MODE;
            }
            cipher.init(encryptMode, secretKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return cipher;
    }


}
