package com.boss.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA 在輸入和輸出時校驗
 */
@Service
@Slf4j
public class RSAService implements InitializingBean {


    @Value("classpath:keys/private-key_pkcs8.pem")
    private Resource privateKey;
    @Value("classpath:keys/public-key.pem")
    private Resource publicKey;

    private String realPrivateKey;
    private String realPublicKey;

    @Override
    public void afterPropertiesSet() throws IOException {
        String privateKeyStr = new String(Files.readAllBytes(Paths.get(privateKey.getFile().getPath())));
        String publicKeyStr = new String(Files.readAllBytes(Paths.get(publicKey.getFile().getPath())));
        realPrivateKey = privateKeyStr.replaceAll("-----END PRIVATE KEY-----", "").replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("\r|\n", "");
        realPublicKey = publicKeyStr.replaceAll("-----END PUBLIC KEY-----", "").replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("\r|\n", "");
        System.out.println("RSA Service afterPropertiesSet");
    }
    //Sign
    public String signSHA256RSA(String input) throws Exception {
        return signSHA256RSA(input,realPrivateKey);
    }
    //Verify
    public boolean verify(String data,String sign) throws Exception {
        return verify(data,realPublicKey,sign);
    }




    public String signSHA256RSA(String input, String realPrivateKey) throws Exception {

        byte[] b1 = Base64.getDecoder().decode(realPrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(input.getBytes("UTF-8"));
        byte[] s = privateSignature.sign();
        return Base64.getEncoder().encodeToString(s);

    }
    public boolean verify(String data, String realPublicKey,String sign) throws Exception {

        //transform the base64 string to byte[]
        byte[] pk = Base64.getDecoder().decode(realPublicKey);
        byte[] sig = Base64.getDecoder().decode(sign);
        X509EncodedKeySpec spec = new X509EncodedKeySpec( pk );
        KeyFactory kf = KeyFactory.getInstance( "RSA" );
        PublicKey pubKey = kf.generatePublic( spec );
        Signature signature = Signature.getInstance( "SHA256withRSA" );
        signature.initVerify( pubKey );
        signature.update( data.getBytes() );
        boolean ret = signature.verify( sig );
        System.out.println(ret);
        return ret;

    }

    /**
     * RSA 公鑰加密
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public String encrypt(String str,String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey= (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RAS加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,pubKey);
        String outStr=Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * 私鑰解密
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public String decrypt(String str,String privateKey) throws Exception {
        //Base64解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        //Base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,priKey);
        String outStr=new String(cipher.doFinal(inputByte));
        return outStr;

    }



}
