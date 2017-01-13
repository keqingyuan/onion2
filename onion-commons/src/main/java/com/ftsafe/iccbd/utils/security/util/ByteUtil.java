package com.ftsafe.iccbd.utils.security.util;

import java.io.*;

public class ByteUtil {

    final static char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F'};

    final static String HEX_ATOM = "0123456789ABCDEF";

    /**
     * 字节数组转成16进制字符串
     *
     * @param d 字节数组
     * @return
     */
    public static String toHexString(byte... d) {
        return (d == null || d.length == 0) ? "" : toHexString(d, 0, d.length);
    }

    /**
     * 字节数组转成16进制字符串
     *
     * @param d 字节数组
     * @param s 字节起始位置
     * @param n 换转字节长度
     * @return
     */
    public static String toHexString(byte[] d, int s, int n) {
        final char[] ret = new char[n * 2];
        final int e = s + n;

        int x = 0;
        for (int i = s; i < e; ++i) {
            final byte v = d[i];
            ret[x++] = HEX[0x0F & (v >> 4)];
            ret[x++] = HEX[0x0F & v];
        }
        return new String(ret);
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param hex 16进制字符串
     * @return
     */
    public static byte[] toBytes(String hex) {
        if (hex == null || hex.equals("")) return null;
        hex = hex.toUpperCase();
        int length = hex.length() / 2;
        byte[] d = new byte[length];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((byte) HEX_ATOM.indexOf(hexChars[pos]) << 4 | (byte) HEX_ATOM.indexOf(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param hex 16进制字符串
     * @return
     */
    public static byte[] toBytes2(String hex) {
        if (hex == null) return null;

        int i = hex.length();
        if (i == 0 || i % 2 != 0) return null;

        int k = 0;
        byte[] result = new byte[i / 2];
        for (k = 0; k < i / 2; k++) {
            String buffer = hex.substring(2 * k, 2 * k + 2);
            int temp = (int) Integer.parseInt(buffer, 16);
            result[k] = (byte) temp;
        }
        return result;
    }


    public static final String objectToHexString(Serializable s) throws IOException {
        return toHexString(objectToBytes(s));
    }

    public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException {
        return bytesToObject(toBytes(hex));
    }

    public static int toInt(byte... b) {
        int ret = 0;
        for (final byte a : b) {
            ret <<= 8;
            ret |= a & 0xFF;
        }
        return ret;
    }

    public static byte[] getRandomKey(int byteLen) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteLen; i++) {
            sb.append(HEX[((int) (15.0D * Math.random()))]);
        }
        return toBytes(sb.toString());
    }

    public static byte[] addBytes(byte[][] src) {
        int length = 0;
        for (int i = 0; i < src.length; i++) {
            if (src[i] != null)
                length += src[i].length;
        }
        byte[] score = new byte[length];
        int index = 0;
        for (int i = 0; i < src.length; i++) {
            if (src[i] != null) {
                System.arraycopy(src[i], 0, score, index, src[i].length);
                index += src[i].length;
            }
        }
        src = (byte[][]) null;
        return score;
    }

    public static byte[] getMidBytes(byte[] src, int startIndex, int length) {
        byte[] b = new byte[length];
        System.arraycopy(src, startIndex, b, 0, length);
        return b;
    }

    public static byte[] getBytes(byte[] data, int len, byte flag) {
        byte[] b = new byte[len];
        for (int i = 0; i < b.length; i++) {
            if (i < len - data.length)
                b[i] = flag;
            else {
                b[i] = data[(i - b.length + data.length)];
            }
        }
        data = (byte[]) null;
        return b;
    }

    public static byte[] getBytes(byte[] data, byte old_flag, byte new_flag) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] != old_flag)
                break;
            data[i] = new_flag;
        }

        return data;
    }


    private static byte toByte(char c) {
        byte b = (byte) HEX_ATOM.indexOf(c);
        return b;
    }


    public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(in);
        Object o = oi.readObject();
        oi.close();
        return o;
    }

    public static final byte[] objectToBytes(Serializable s) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream ot = new ObjectOutputStream(out);
        ot.writeObject(s);
        ot.flush();
        ot.close();
        return out.toByteArray();
    }

    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xF0) >>> 4));
            temp.append((byte) (bytes[i] & 0xF));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte[] abt = new byte[len];
        if (len >= 2) {
            len /= 2;
        }

        byte[] bbt = new byte[len];
        abt = asc.getBytes();

        for (int p = 0; p < asc.length() / 2; p++) {
            int j;
            if ((abt[(2 * p)] >= 48) && (abt[(2 * p)] <= 57)) {
                j = abt[(2 * p)] - 48;
            } else {
                if ((abt[(2 * p)] >= 97) && (abt[(2 * p)] <= 122))
                    j = abt[(2 * p)] - 97 + 10;
                else
                    j = abt[(2 * p)] - 65 + 10;
            }
            int k;
            if ((abt[(2 * p + 1)] >= 48) && (abt[(2 * p + 1)] <= 57)) {
                k = abt[(2 * p + 1)] - 48;
            } else {
                if ((abt[(2 * p + 1)] >= 97) && (abt[(2 * p + 1)] <= 122))
                    k = abt[(2 * p + 1)] - 97 + 10;
                else {
                    k = abt[(2 * p + 1)] - 65 + 10;
                }
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public static byte[] getMultiples(byte[] src, int multiples, byte b) {
        int remnant = src.length % multiples;
        if (remnant == 0) {
            return src;
        }
        int quotient = src.length / multiples;
        byte[] newByte = new byte[(quotient + 1) * multiples];
        for (int i = 0; i < newByte.length; i++) {
            if (i < src.length)
                newByte[i] = src[i];
            else {
                newByte[i] = b;
            }
        }
        return newByte;
    }

}
