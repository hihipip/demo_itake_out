package com.boss.demo.apicontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/rsa")
public class RSAController {

    private Logger logger = LoggerFactory.getLogger(RSAController.class);

    @Value("classpath:keys/private-key_pkcs8.pem")
    Resource privateKey;
    @Value("classpath:keys/public-key.pem")
    Resource publicKey;

    //另一種載入方式
    public Resource loadEmployeesWithClassPathResource() {
        return new ClassPathResource("keys/private-key.pem");
    }

    @RequestMapping("/key")
    public String getKey() throws Exception{

        logger.debug("測試 debug tag");
        logger.info("測試 info tag");
        logger.warn("測試 warn tag");
        logger.error("測試 error tag");

        String privateKeyStr = new String(Files.readAllBytes(Paths.get(privateKey.getFile().getPath())));
        String publicKeyStr = new String(Files.readAllBytes(Paths.get(publicKey.getFile().getPath())));
        String realPrivateKey = privateKeyStr.replaceAll("-----END PRIVATE KEY-----", "").replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("\r|\n", "");
        String realPublicKey = publicKeyStr.replaceAll("-----END PUBLIC KEY-----", "").replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("\r|\n", "");

        System.out.println(realPrivateKey);
        System.out.println(realPublicKey);

        String str = "str";
        for(int i=0;i<1000;i++){
            str+="str"+i;
        }

        String sign = RSAController.signSHA256RSA(str,realPrivateKey);
        boolean verify = RSAController.verify(str,realPublicKey,sign);

        String data = "HelloWorld";
        System.out.println(data);
        String encryptData = RSAController.encrypt(data,realPublicKey);
        System.out.println(encryptData);
        String decryptData = RSAController.decrypt(encryptData,realPrivateKey);
        System.out.println(decryptData);



        return verify+"";
    }







    public static String signSHA256RSA(String input, String realPrivateKey) throws Exception {

        byte[] b1 = Base64.getDecoder().decode(realPrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(input.getBytes("UTF-8"));
        byte[] s = privateSignature.sign();
        return Base64.getEncoder().encodeToString(s);

    }
    public static boolean verify(String data, String realPublicKey,String sign) throws Exception {

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
    public static String encrypt(String str,String publicKey) throws Exception {
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
    public static String decrypt(String str,String privateKey) throws Exception {
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
