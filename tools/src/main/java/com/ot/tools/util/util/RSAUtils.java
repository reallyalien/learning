package com.ot.tools.util.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * </p>
 *
 * @author wyh
 * @version 1.0
 * @date 2019-9-6
 */
@SuppressWarnings("Duplicates")
public class RSAUtils {

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String pubKey;
    public static String priKey;

    static {
        try {
            Map<String, Object> map = genKeyPair();
            pubKey = getPublicKey(map);
            priKey = getPrivateKey(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = Base64.decodeBase64(data);
            temp = new String(RSAUtils.decryptByPrivateKey(rs, PRIVATEKEY), "UTF-8"); //以utf-8的方式生成字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

//    public static void main(String[] args) throws Exception {
//        //生成公钥,私钥
//        long start = System.currentTimeMillis();
//        Map<String, Object> pair = genKeyPair();
//        String pri = getPrivateKey(pair);
//        String pub = getPublicKey(pair);
//        System.out.println(pri);
//        System.out.println(pub);
//        System.out.println("----------------------");
//        System.out.println("生成秘钥耗时为:" + (System.currentTimeMillis() - start));
//        //对需要加密的字符串获取字节码后加密
//        byte[] res1 = encryptByPublicKey("cardNo=SW1000007991&clinicCode=SWG49726".getBytes(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSFkWonUQixs9yy4GSU99Kibj/oxyzmNFpCiPMqYwP0Yqda7C2UlKpDuDm0gVPLUGoP8tzqPp58x+EVT+EDx9KNf9Qc1H6XW1RJDeJeKYLQg+CQXdQTRIvkEOHrH8AAEFmc6v6rvBzQmu7RPCutkjDsLtcKfytGv6FlGfJS33lkwIDAQAB");
//        System.out.println("base64编码结果为：" + Base64.encodeBase64String(res1));
//        //对需要解密的字节数组解密后按照utf-8编码
//        String res = new String(RSAUtils.decryptByPrivateKey(res1, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJIWRaidRCLGz3LLgZJT30qJuP+jHLOY0WkKI8ypjA/Rip1rsLZSUqkO4ObSBU8tQag/y3Oo+nnzH4RVP4QPH0o1/1BzUfpdbVEkN4l4pgtCD4JBd1BNEi+QQ4esfwAAQWZzq/qu8HNCa7tE8K62SMOwu1wp/K0a/oWUZ8lLfeWTAgMBAAECgYBqXosz/ebJx6bvOBzNVgKk/GjWwoa4gpIgAIEWmXFhZzqd6vbNml2TuUCq8Mtq3lILVwAl5CkVOKB/M68+0d/+iATDo3RpXDUSmHq/IBDGigFPP79VZ804BRTGrcGh/7x49w6OywDLMfA5BmTeQJ2B8Qb6W8WU8mqAu/GMOAKk4QJBAMyzxA4jTUmt39ipgXTK3TJBM1h4TvVtiafl1sw0yyXef/5uKfuLYyPygMJ7/z1M9HOl2zBtVDp/a1VSMQwYHGMCQQC2siwfsq0uXYbBqb9ngbma63V4uZ9Lr+o8nA1MCJuUmslMOwggUTp8Xs6yTJM/ALh735NL+TCLeqNkwmh/m+ERAkEAySQ3GKFNl4F3vEDLCQUxlukEWAFXrMfmhIlOqDeUCnD4mfyL2jVbxH3MUc5fvk94wI6Ps6UqJ5T+1ca+lSC3+wJAdZZFyf0Rc+ckZZ9gnzkigfCMgjBOs8M8qtfxwv9DS6k4aIUSHOLLFuNae+3HU+Sd2LS0tFBli1Bau+5YndFxUQJADyXDAIP22vY+Z+SBZFd2VUMZAelRMOkwLN4ErXqSuz4psJDCiRqXxfyeLmlwZ7qf+38VSsyhg5ov/NMfAkd9Ow=="), GlobalConstants.CHARSET);
//        System.out.println(res);
//        System.out.println(res.length());
//    }

}