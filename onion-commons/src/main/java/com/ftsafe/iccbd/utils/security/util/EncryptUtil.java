package com.ftsafe.iccbd.utils.security.util;

import com.ftsafe.iccbd.utils.security.sm4.SM4;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jcajce.provider.digest.SM3;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * DES加密工具类
 *
 * @author Warmsheep
 */
public class EncryptUtil {

    private static final String model = "DESede/ECB/NoPadding";

    /**
     * DES解密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String desDecrypt(String message, String key) throws Exception {
        try {
            byte[] keyBytes = null;
            if (key.length() == 16) {
                keyBytes = newInstance8Key(ByteUtil.toBytes(key));
            } else if (key.length() == 32) {
                keyBytes = newInstance16Key(ByteUtil.toBytes(key));
            } else if (key.length() == 48) {
                keyBytes = newInstance24Key(ByteUtil.toBytes(key));
            }
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            Cipher c1 = Cipher.getInstance(model);
            c1.init(2, deskey);
            byte[] retByte = c1.doFinal(ByteUtil.toBytes(message));

            return new String(retByte);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String desDecryptToHexString(String message, String key) throws Exception {
        try {
            byte[] keyBytes = null;
            if (key.length() == 16) {
                keyBytes = newInstance8Key(ByteUtil.toBytes(key));
            } else if (key.length() == 32) {
                keyBytes = newInstance16Key(ByteUtil.toBytes(key));
            } else if (key.length() == 48) {
                keyBytes = newInstance24Key(ByteUtil.toBytes(key));
            }
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            Cipher c1 = Cipher.getInstance(model);
            c1.init(2, deskey);
            byte[] retByte = c1.doFinal(ByteUtil.toBytes(message));

            return ByteUtil.toHexString(retByte);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }


    /**
     * DES加密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String message, String key) throws Exception {
        byte[] keyBytes = null;
        if (key.length() == 16) {
            keyBytes = newInstance8Key(ByteUtil.toBytes(key));
        } else if (key.length() == 32) {
            keyBytes = newInstance16Key(ByteUtil.toBytes(key));
        } else if (key.length() == 48) {
            keyBytes = newInstance24Key(ByteUtil.toBytes(key));
        }

        SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");

        Cipher cipher = Cipher.getInstance(model);
        cipher.init(1, deskey);
        return ByteUtil.toHexString(cipher.doFinal(message.getBytes("UTF-8")));
    }

    public static String desEncryptHexString(String message, String key) throws Exception {
        byte[] keyBytes = null;
        if (key.length() == 16) {
            keyBytes = newInstance8Key(ByteUtil.toBytes(key));
        } else if (key.length() == 32) {
            keyBytes = newInstance16Key(ByteUtil.toBytes(key));
        } else if (key.length() == 48) {
            keyBytes = newInstance24Key(ByteUtil.toBytes(key));
        }

        SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");

        Cipher cipher = Cipher.getInstance(model);
        cipher.init(1, deskey);
        return ByteUtil.toHexString(cipher.doFinal(ByteUtil.toBytes(message)));
    }

    /**
     * sha1Hex加密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String sha1HexEncrypt(String message) throws Exception {
        return DigestUtils.sha1Hex(message);
    }

    /**
     * 密码加密，先SHA加密，然后使用DES加密
     *
     * @param password
     * @param key
     * @return
     * @throws Exception
     */
    public static String passwordEncrypt(String password, String key) throws Exception {
        password = DigestUtils.sha1Hex(password);
        password = desEncrypt(password, key);
        return password;
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String md5Encrypt(String message) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(message.getBytes());
        String hexValue = ByteUtil.toHexString(md5Bytes);
        return hexValue;

    }


    private static byte[] newInstance24Key(byte[] key) {
        if ((key != null) && (key.length == 24)) {
            return key;
        }
        System.err.println("密钥长度有误,期望值[24]");
        return null;
    }

    private static byte[] newInstance16Key(byte[] key) {
        if ((key != null) && (key.length == 16)) {
            byte[] b = new byte[24];
            System.arraycopy(key, 0, b, 0, 16);
            System.arraycopy(key, 0, b, 16, 8);
            key = (byte[]) null;
            return b;
        }
        System.err.println("密钥长度有误,期望值[16]");
        return null;
    }

    private static byte[] newInstance8Key(byte[] key) {
        if ((key != null) && (key.length == 8)) {
            byte[] b = new byte[24];
            System.arraycopy(key, 0, b, 0, 8);
            System.arraycopy(key, 0, b, 8, 8);
            System.arraycopy(key, 0, b, 16, 8);
            key = (byte[]) null;
            return b;
        }
        System.err.println("密钥长度有误,期望值[8]");
        return null;
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static String xor(String p1, String p2) {
        byte[] b1 = ByteUtil.toBytes(p1), b2 = ByteUtil.toBytes(p2);
        if (b1.length != b2.length) {
            System.err.println("参与异或的数据长度必须一致，参数一的长度：" + b1.length + "参数二的长度：" + b2.length);
            return null;
        }
        byte[] buf = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            buf[i] = (byte) (b1[i] ^ b2[i]);
        }

        return ByteUtil.toHexString(buf);
    }

    /**
     * @param message   消息
     * @param key       密钥
     * @param algorithm 算法，加密：1，解密：0
     * @return
     */
    public static String sm4DecryptEncrypt(String message, String key, int algorithm) {
        if (key != null && key.length() == 32) {
            if (message != null && message.length() == 32) {
                byte[] msg = ByteUtil.toBytes(message);
                byte[] keys = ByteUtil.toBytes(key);
                byte[] out = new byte[16];
                new SM4().sm4(msg, msg.length, keys, out, algorithm);
                return ByteUtil.toHexString(out);
            }
            System.out.println("消息长度有误，期望长度[16]");
            return null;
        }
        System.out.println("密钥长度有误，期望长度[16]");
        return null;
    }

//    public static void main(String[] args) throws Exception {
//        // 加密密码测试DEMO
////		System.out.println(md5Encrypt("湖南"));
//        String key = "0123456789ABCDEFFEDCBA9876543210";
//        String password = "11111111";
//        System.out.println("密钥:" + key + "长度:" + String.format("%04x", key.length()));
//        System.out.println("加密前的明文:" + password);
//
//        String desText = "";
//        try {
//            String sha1Text = sha1HexEncrypt(password);
//            System.out.println("使用sha1加密后的密文:" + sha1Text);
//            desText = desEncrypt(sha1Text, key);
//            System.out.println("使用des加密后的密文:" + desText);
//            System.out.println("使用des解密后的明文:" + desDecrypt(desText, key));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
