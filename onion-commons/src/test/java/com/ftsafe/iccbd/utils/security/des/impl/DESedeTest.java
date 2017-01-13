package com.ftsafe.iccbd.utils.security.des.impl;

import com.ftsafe.iccbd.utils.security.util.ByteUtil;
import com.ftsafe.iccbd.utils.security.util.EncryptUtil;
import com.ftsafe.iccbd.utils.security.util.OddEvenCheckUtil;
import junit.framework.TestCase;
import org.jpos.iso.ISOUtil;

/**
 * Created by qingyuan on 16/12/31.
 */
public class DESedeTest extends TestCase {
    public void testNewInstance16() throws Exception {
        DESede des = DESede.newInstance16(ByteUtil.toBytes("ED3371FFE1F4ECDA5B989BE4C8D399DA"));

        byte[] encrypted = des.encrypt(ByteUtil.toBytes("ED3371FFE1F4ECDA5B989BE4C8D399DA"));

        System.out.println(ByteUtil.toHexString(encrypted));

        String key = "ED3371FFE1F4ECDA5B989BE4C8D399DA";

        String message = "96784FEC32A320D19A267EC2FA1878CB";

        String enc = EncryptUtil.desEncryptHexString(message, key);
        System.out.println(enc);
        String dec = EncryptUtil.desDecryptToHexString(enc, key);
        System.out.println(dec);

        message = "12345689";
        enc = EncryptUtil.desEncrypt(message, key);
        System.out.println(enc);
        dec = EncryptUtil.desDecrypt(enc, key);
        System.out.println(dec);

    }

    public void testXor() {
        try {
            String fa1 = "2C3336885BC6507D4768BD00DB425132";
            String fa2 = "46181668EDA37C89B1D6A34E5061351B";
            String key = EncryptUtil.xor(fa2,fa1);
            System.out.println("fa1："+fa1);
            System.out.println("fa2："+fa2);
            System.out.println("key："+key);
            System.out.println("fa1奇偶校验："+ ByteUtil.toHexString(OddEvenCheckUtil.parityOfOdd(ByteUtil.toBytes(fa1),0)));
            System.out.println("fa2奇偶校验："+ ByteUtil.toHexString(OddEvenCheckUtil.parityOfOdd(ByteUtil.toBytes(fa2),0)));
            System.out.println("key奇偶校验："+ ByteUtil.toHexString(OddEvenCheckUtil.parityOfOdd(ByteUtil.toBytes(key),0)));
            System.out.println("===============================");
            fa1 = "2C3237895BC7517C4668BC01DA435132";
            fa2 = "46191668ECA27C89B0D6A24F5161341A";
            key = EncryptUtil.xor(fa2,fa1);
            System.out.println("fa1:"+fa1);
            System.out.println("fa2:"+fa2);
            System.out.println("key:"+key);
            System.out.println("fa1奇偶校验:"+ ByteUtil.toHexString(OddEvenCheckUtil.parityOfOdd(ByteUtil.toBytes(fa1),0)));
            System.out.println("fa2奇偶校验:"+ ByteUtil.toHexString(OddEvenCheckUtil.parityOfOdd(ByteUtil.toBytes(fa2),0)));
            System.out.println("key奇偶校验:"+ ByteUtil.toHexString(OddEvenCheckUtil.parityOfOdd(ByteUtil.toBytes(key),0)));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}