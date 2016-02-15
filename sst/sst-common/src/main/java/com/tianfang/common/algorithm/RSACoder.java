package com.tianfang.common.algorithm;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;

import com.tianfang.common.base64.Base64Coder;
import com.tianfang.common.base64.UrlBase64Coder;
import com.tianfang.common.util.ArrayUtils;
import com.tianfang.common.util.StringUtils;

public class RSACoder {

	private static final String PRIVATE_KEY = "private";
	
	public static final String PUBLIC_KEY = "public";
	
	/** 
     * 生成公钥和私钥 
     * @throws NoSuchAlgorithmException  
     * 
     */  
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        map.put(PUBLIC_KEY, publicKey);  
        map.put(PRIVATE_KEY, privateKey);  
        return map;  
    }  
    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        List<byte[]> datas =  StringUtils.splitString(data, key_len - 11);  
       
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        byte[] all = null;
        for (byte[] s : datas) {  
        	byte[] mb = cipher.doFinal(s);
        	all = ArrayUtils.addAll(all, mb);
        }  
        
        mi = UrlBase64Coder.encode(all);
        return mi;  
    }  
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
       // byte[] bytes = data.getBytes();  
        //byte[] bcd = ASCII_To_BCD(bytes, bytes.length);  
        byte[] bcd = Base64Coder.decodeToByte(data);
        System.err.println(bcd.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        //byte[][] arrays = splitArray(bcd, key_len);  
        List<byte[]> arrays = ArrayUtils.splitByteArray(bcd, key_len);
        byte[] all = null;
        for(byte[] arr : arrays){  
        	byte[] mb = cipher.doFinal(arr);
        	all = ArrayUtils.addAll(all, mb);
        }  
        ming = new String(all);  
        return ming;  
    }  
    
    
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
       /* HashMap<String, Object> map = RSACoder.getKeys();  
        //生成公钥和私钥  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
         */
        //模  
        String modulus = "116262705441292461767938684378745573385265261433835354533161260570850418836270718461079468519086567371916662924537118880790955592987214794462208450157461042923039456080271600322310596059357051099905562221749131076225607808428923699762172481968262836388564574671403872476197309991942349096313039457035406478567";  
        //公钥指数  
        String public_exponent = "65537";  
        //私钥指数  
        String private_exponent = "29771285269630439192968048602226348666425402709654468769939305657872831055896595773591034693185693175389557611724398874794906949685085351490986499390306404869838845033947919691721307992734678201041388835183007534056440442570362142578136442199597719747865426902120172091441361966348776310969207773895064659473";  
        System.out.println("modulus="+modulus);
        System.out.println("public_exponent="+public_exponent);
        System.out.println("private_exponent="+private_exponent);
        
        //明文  
        String ming = "{ aaa : 123456789, bbb : \"血糯米奶茶在微博上被称为“沪上最好喝的奶茶”。据说店主阿姨每天四五点就起床煮奶茶，用传统大锅烧煮，可以说十多年口味始终如一，血糯米量多，奶香浓厚，价格却很亲民，性价比颇高。　茶奶相融的浓郁丝滑，让人不禁间就会想起它！！奶茶控们，沪上最好喝的奶茶你们都喝过了嘛？\" }";  
        //使用模和指数生成公钥和私钥  
        RSAPublicKey pubKey = RSACoder.getPublicKey(modulus, public_exponent);  
        RSAPrivateKey priKey = RSACoder.getPrivateKey(modulus, private_exponent);  
        //加密后的密文  
        String mi = RSACoder.encryptByPublicKey(ming, pubKey);  
        System.err.println("公钥加密密文 : " + mi);  
        //解密后的明文  
        String mingw = RSACoder.decryptByPrivateKey(mi, priKey);  
        System.err.println("私钥解密明文: " + mingw);  
    } 
}
