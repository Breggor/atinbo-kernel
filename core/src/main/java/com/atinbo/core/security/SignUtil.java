package com.atinbo.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUtil {
    protected static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    private static final SimpleDateFormat FORMAT_YYMMDD = new SimpleDateFormat("yyyyMMdd");
    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private final static String DEFAULT_ENCODING = "UTF-8";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDay_yyyyMMdd() {
        return FORMAT_YYMMDD.format(new Date());
    }

    /**
     * 获取MD5加密随机字符串
     *
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), DEFAULT_ENCODING);
    }

    /**
     * 获取时间戳（毫秒）
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
//		return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * URL编码 (符合FRC1738规范)
     *
     * @param input 待编码的字符串
     * @return 编码后的字符串
     */
    public static String encodeUrl(String input) throws UnsupportedEncodingException {
        logger.debug("sign input: {}", input);
//    	return URLEncoder.encode(input, Constant.DEFAULT_ENCODING).replace("+", "%20").replace("*", "%2A");
        return input;
    }

    /**
     * 生成签名
     *
     * @param reqMap 参数
     * @param secret 密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static String makeSign(Map<String, Object> reqMap, String secret)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        logger.debug("sign secret: {}", secret);
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(DEFAULT_ENCODING), mac.getAlgorithm());
        mac.init(secretKey);

        String mk = makeSource(reqMap);
        byte[] hash = mac.doFinal(mk.getBytes(DEFAULT_ENCODING));

        return new String(Base64Encoder.encode(hash));
    }

    /*
     * 生成签名所需源串
     * @param reqMap 请求参数
     * @return 签名所需源串
     */
    public static String makeSource(Map<String, Object> reqMap) throws UnsupportedEncodingException {
        // 确保不含sign
        reqMap.remove("sign");
        Object[] keys = reqMap.keySet().toArray();
        Arrays.sort(keys);

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            Object value = reqMap.get(key);
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                buffer.append(key).append("=").append(value);
                if (i != keys.length - 1) {
                    buffer.append("&");
                }
            }
        }

        return encodeUrl(buffer.toString());
    }

    public static boolean verifySign(Map<String, Object> reqMap, String secret, String sign)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        // 确保不含sign
        reqMap.remove("sign");
//        // 按照接口的编码规则对value编码
//        codePayValue(reqMap);
        // 计算签名
        String generateSign = makeSign(reqMap, secret);
        logger.debug("generateSign: {}", generateSign);
        return generateSign.equals(sign);
    }

    /*
     * 对传来的参数value值先进行一次编码方法，用于验签
     * (编码规则为：除了 0~9 a~z A~Z !*() 之外其他字符按其ASCII码的十六进制加%进行表示，例如“-”编码为“%2D”)
     */
    public static void codePayValue(Map<String, Object> reqMap) {
        Set<String> keySet = reqMap.keySet();
        Iterator<String> itr = keySet.iterator();

        while (itr.hasNext()) {
            String key = (String) itr.next();
            String value = (String) reqMap.get(key);
            value = encodeValue(value);
            reqMap.put(key, value);
        }
    }

    /*
     * 编码规则
     */
    public static String encodeValue(String s) {
        String rexp = "[0-9a-zA-Z!*\\(\\)]";
        StringBuffer sb = new StringBuffer(s);
        StringBuffer sbRtn = new StringBuffer();
        Pattern p = Pattern.compile(rexp);
        char temp;
        String tempStr;

        for (int i = 0; i < sb.length(); i++) {
            temp = sb.charAt(i);
            tempStr = String.valueOf(temp);
            Matcher m = p.matcher(tempStr);

            boolean result = m.find();
            if (!result) {
                tempStr = hexString(tempStr);
            }
            sbRtn.append(tempStr);
        }

        return sbRtn.toString();
    }

    /*
     * 十六进制编码
     */
    private static String hexString(String s) {
        byte[] b = s.getBytes();
        String retStr = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            retStr = "%" + hex.toUpperCase();
        }
        return retStr;
    }
}
