package com.atinbo.core.security;

import java.security.MessageDigest;

/**
 * MD5加密组件，提供加密为32位，或16位
 *
 * @author guojd
 * @version 1.0
 * @since 1.0
 */
public class MD5Encoder {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private static final String ALGORITHM = "MD5";
    private static final String CHARSET = "UTF-8";
    private Object salt;

    /**
     * 构造MD5对象
     *
     * @param salt 盐值
     */
    public MD5Encoder(Object salt) {
        this.salt = salt;
    }

    /**
     * 不需要盐值的MD5位数为32的加密
     *
     * @param SourceString
     * @return
     * @throws Exception
     */
    public static String encodeBit32WithNoSalt(byte[] SourceString) throws Exception {
        MessageDigest digest = java.security.MessageDigest.getInstance(ALGORITHM);
        digest.update(SourceString);
        byte messageDigest[] = digest.digest();
        return byteArrayToHexString(messageDigest);
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        String salt = "helloworld";
        MD5Encoder encoderMd5 = new MD5Encoder(salt);
        String encode = encoderMd5.encode32bit("test");
        System.out.println(encode);
        boolean passwordValid = encoderMd5.isStrValid("083A8DB3FF5B9B4ECE3EF2BDE03226C8", "test");
        System.out.println(passwordValid);
    }

    /**
     * 加密
     *
     * @param rawPass 字节数组-原串
     * @return
     */
    public String encode(byte[] rawPass) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            // 加密后的字符串
            result = byteArrayToHexString(md.digest(mergeStrAndSalt(rawPass).getBytes(CHARSET)));
        } catch (Exception ex) {
        }
        return result;
    }

    public boolean isStrValid(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encode32bit(rawPass);
        return pass1.equals(pass2);
    }

    /**
     * 合并要加密的字符串和盐值
     *
     * @param bStr 要加密的串
     * @return 合并的字符串
     */
    private String mergeStrAndSalt(byte[] bStr) {
        String str = new String(bStr);
        if ((salt == null) || "".equals(salt)) {
            return str;
        } else {
            return str + "{" + salt.toString() + "}";
        }
    }

    /**
     * 加密为32位的字符串
     *
     * @param rawPass 原字符串
     * @return 加密串
     */
    public String encode32bit(String rawPass) {
        return encode(rawPass.getBytes());
    }

    /**
     * 加密为16位的字符串
     *
     * @param rawPass 原字符串
     * @return 加密串
     */
    public String encode16bit(String rawPass) {
        String encodeStr = encode(rawPass.getBytes());
        return encodeStr.substring(8, 24);
    }

}