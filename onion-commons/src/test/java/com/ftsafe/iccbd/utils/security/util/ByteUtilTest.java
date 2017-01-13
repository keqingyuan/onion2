package com.ftsafe.iccbd.utils.security.util;

import com.ftsafe.iccbd.utils.StringUtil;
import junit.framework.TestCase;

/**
 * Created by qingyuan on 17/1/7.
 */
public class ByteUtilTest extends TestCase {

    private String msg = "ab34feac";

    private byte[] bmsg = {(byte) 0xef, (byte) 0xFF, 0x37};

    //11+10+10+8+11=5ms
    //10+12+14+20+11=13.4
    //10+16+9+11+18=12.8
    public void testToBytes() throws Exception {
//        for (int i = 0; i < 500; i++)
//            ByteUtil.toBytes(msg);

    }
    //15+17+10+11+18=14.2
    //16+9+9+15+16=13
    //13+12+12+9+7+9+12+8+8+7+11+9
    //11+7+19+8+7+15
    //7+7+7=7
    public void testToBytes3() throws Exception {
        go: {
            for (int i = 0; i < 500; i++) {
//                byte[] b = ByteUtil.toBytes(msg);
//                System.out.print(ByteUtil.toHexString(b) + " ");
                if (i == 100)
                    break go;
            }
            System.out.println("out for");
        }
        System.out.println("out go");

    }
    //15+10+11+13+7=11.2
    //8+11+8+9+10=9.2
    //7+6+13+8+7=8.2
    //9+10+12+12+13+17+33+12+7+17+14+10
    //7+17+13+9+12+10
    public void testToBytes4() throws Exception {
        for (int i = 0; i < 500; i++) {
            byte[] b = ByteUtil.toBytes2(msg);
            System.out.print(ByteUtil.toHexString(b)+" ");
        }

    }

    // 24+21+21+28+24=23.6
    // 17+22+18+20+24
    public void testToHexString() throws Exception {
        for (int i = 0; i < 500; i++) {
            String s = ByteUtil.toHexString(bmsg);
            System.out.print(s);
        }
    }

    // 17+17+23+19+17=18.6
    public void testToHexString2() throws Exception {
        for (int i = 0; i < 500; i++) {
            String s = ByteUtil.toHexString(bmsg);
            System.out.print(s);
        }
    }

    // 21+26+25+27+27=25.2
    public void testToHexString3() throws Exception {
//        for (int i = 0; i < 500; i++) {
//            String s = ByteUtil.toHexString(bmsg);
//            System.out.print(s);
//        }
    }

}