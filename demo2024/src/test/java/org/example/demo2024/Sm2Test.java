package org.example.demo2024;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.setting.dialect.Props;
import com.antherd.smcrypto.sm2.Keypair;
import com.antherd.smcrypto.sm2.Sm2;
import org.example.demo2024.util.SM2Utils;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @program: demo2024
 * @description: sm2算法
 * @author: 作者名
 * @create: 2024/02/18
 */
public class Sm2Test {
    private static final Props props = new Props("application.properties");


    @Test
    void test1(){

//        Keypair keypair = Sm2.generateKeyPairHex();
//        String privateKey = keypair.getPrivateKey(); // 公钥
//        String publicKey = keypair.getPublicKey(); // 私钥
//        System.err.println(privateKey);
//        System.err.println(publicKey);

        String publicKey = props.getProperty("sm2.public-key");

        String privateKey = props.getProperty("sm2.private-key");

        // cipherMode 1 - C1C3C2，0 - C1C2C3，默认为1
        String msg = "aaa123456";
        String encryptData = Sm2.doEncrypt(msg, publicKey); // 加密结果
        System.err.println(encryptData);
        String decryptData = Sm2.doDecrypt(encryptData, privateKey); // 解密结果
        System.err.println(decryptData);

    }


}
