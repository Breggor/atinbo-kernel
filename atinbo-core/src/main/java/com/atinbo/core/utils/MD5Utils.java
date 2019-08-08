package com.atinbo.core.utils;

import java.security.MessageDigest;

/**
 * MD5 utils.
 *
 * @author huangxiaofeng
 */
public class MD5Utils {

    /**
     * Md 5 string.
     *
     * @param src     the src
     * @param charset the charset
     * @return the string
     */
    private static String md5(String src, String charset) {
        MessageDigest md5;
        StringBuilder hexValue = new StringBuilder(32);
        try {
            md5 = MessageDigest.getInstance("MD5");

            byte[] byteArray;
            byteArray = src.getBytes(charset);

            byte[] md5Bytes = md5.digest(byteArray);

            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return hexValue.toString();
    }

    /**
     * Md 5 string.
     *
     * @param src the src
     * @return the string
     */
    public static String md5(String src) {
        return md5(src, "UTF-8");
    }
}