package com.tuling.algorithm.sort.secret;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyongkang
 * @date Create in 2022/6/8 15:36
 * @desc
 * RSA是非对称加密算法，首先生成配对的公钥和私钥，然后通过公钥进行加密，通过私钥进行解密。
 *
 * 非对称加密：
 * 优点：非对称加密与对称加密相比其安全性更好，只要私钥不泄露，很难被破解。
 * 缺点：加密和解密花费时间长、速度慢，只适合对少量数据进行加密。
 *
 *
 * RSA是目前最有影响力和最常用的公钥加密算法。它能够抵抗到目前为止已知的绝大多数密码攻击，已被ISO推荐为公钥数据加密标准。
 * RSA公开密钥密码体制的原理是：根据数论，寻求两个大素数比较简单，而将它们的乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥
 *
 */
public class RSATest {

    public static void main(String[] args) throws Exception {
        String content = "测试RSA加密算法";

        Map<String, String> rsaKey = createRsaKey();
        String secretContent = encrypt(content, rsaKey.get("publicKey"));
        System.out.println("加密后：" + secretContent);

        String publicContent = decrypt(secretContent, rsaKey.get("privateKey"));
        System.out.println("解密后：" + publicContent);

    }

    /**
     * RSA加密
     * @return
     */
    public static String encrypt(String content, String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        KeyFactory rsaFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] result = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * RSA解密
     * @return
     */
    public static String decrypt(String content, String privateKeyStr) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
        KeyFactory rsaFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content.getBytes()));
        return new String(result);
    }



    /**
     * 生成一对秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, String> createRsaKey() throws Exception {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 初始化秘钥生成器，大小为96~1024位
        rsaKeyPairGenerator.initialize(1024);
        // 生成一对秘钥
        KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("privateKey", privateKeyStr);
        keyMap.put("publicKey", publicKeyStr);

        System.out.println("生成私钥：" + privateKeyStr);
        System.out.println("生成公钥：" + publicKeyStr);
        return keyMap;
    }

}
