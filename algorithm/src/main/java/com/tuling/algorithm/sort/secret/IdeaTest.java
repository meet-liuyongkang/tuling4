package com.tuling.algorithm.sort.secret;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Base64;

/**
 * @author liuyongkang
 *
 * 对称加密算法
 * 优点：算法对消息双方公开、计算量小、加密速度快、加密效率高。
 * 缺点：在数据传送前，发送方和接收方必须商定好秘钥，然后双方保存好秘钥。如果一方的秘钥被泄露，那么加密信息就会被破解。
 *
 * 这种算法是在DES算法的基础上发展出来的，类似于三重DES。
 * 发展IDEA也是因为感到DES具有密钥太短等缺点。
 * DEA的密钥为128位，这么长的密钥在今后若干年内应该是安全的。
 * 在实际项目中用到的很少了解即可。
 */
public class IdeaTest {
    public static void main(String[] args) {
        bcIDEA();
    }
    public static void bcIDEA() {
        String src = "www.xttblog.com security ideasadasdas";
        try {
            Security.addProvider(new BouncyCastleProvider());

            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("IDEA");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            //转换密钥
            Key key = new SecretKeySpec(keyBytes, "IDEA");

            //加密
            Cipher cipher = Cipher.getInstance("IDEA/ECB/ISO10126Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc idea encrypt : " + new String(Base64.getEncoder().encode(result)));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("bc idea decrypt : " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

