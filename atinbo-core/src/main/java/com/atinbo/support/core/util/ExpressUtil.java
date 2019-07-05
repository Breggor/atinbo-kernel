package com.atinbo.support.core.util;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author fangqy
 * @ClassName: ExpressUtil
 * @Description: 物流工具类
 * @date 2016年8月24日 下午3:11:27
 */
public class ExpressUtil {
    protected static final Logger logger = LoggerFactory.getLogger(ExpressUtil.class);
    // 获得MD5摘要算法的 MessageDigest 对象
    private static MessageDigest _mdInst = null;
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * @param @param  expressNo 快递单号
     * @param @param  expressCode 快递公司code
     * @param @param  expressName 快递公司名称
     * @param @param  key 快递100授权码
     * @param @param  customer 快递100分配的的公司编号
     * @param @param  deliveryTime 发货时间
     * @param @return
     * @return String
     * @throws
     * @Title: getExpressMessageByOrder
     * @Description: 获取快递物流信息
     * @author fangqy
     */
    public static String getExpressMessageByOrder(String expressNo, String expressCode, String expressName, String key, String customer, String deliveryTime) {
        String ret = "";
        if (StringUtils.isEmpty(expressNo)) {
            throw new RuntimeException("快递单号不能为空");
        }
        if (StringUtils.isEmpty(expressCode)) {
            throw new RuntimeException("快递公司code不能为空");
        }
        if (StringUtils.isEmpty(expressName)) {
            throw new RuntimeException("快递公司名称不能为空");
        }
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("快递100授权码不能为空");
        }
        if (StringUtils.isEmpty(customer)) {
            throw new RuntimeException("快递100分配的的公司编号不能为空");
        }

        StringBuilder param = new StringBuilder("{");
        param.append("\"com\":\"").append(expressCode).append("\",\"num\":\"")
                .append(expressNo).append("\"}");

        String sign = encode(param.toString() + key + customer);//签名,用于验证身份，按param +key+customer的顺序进行MD5加密

        Map<String, String> params = new HashMap<String, String>();
        params.put("param", param.toString());
        params.put("customer", customer);
        params.put("sign", sign);
        //通过HttpClient发送获取物流信息请求
        String msg = HttpClientUtil.doPost("http://poll.kuaidi100.com/poll/query.do", params);
        JSONObject jsonMsg = new JSONObject(msg);
        String object = (String) jsonMsg.get("message");

        StringBuilder deliveryMsg = new StringBuilder("{\"context\":\"您的宝贝已经已发货啦 \",\"time\":\"");
        deliveryMsg.append(deliveryTime).append("\",\"ftime\":\"")
                .append(deliveryTime).append("\"}");

        JSONObject deliveryMsgJson = new JSONObject(deliveryMsg.toString());
        JSONArray data = null;
        if (!"ok".equals(object)) {
            data = new JSONArray();
            data.put(deliveryMsgJson);
            ret = "{\"expressName\":\"" + expressName + "\",\"expressNo\":\"" + expressNo + "\",\"expressMsg\":" + data + "}";
            return ret;
        }
        //物流信息后面追加发货信息
        data = (JSONArray) jsonMsg.get("data");
        data.put(data.length(), deliveryMsgJson);
        ret = "{\"expressName\":\"" + expressName + "\",\"expressNo\":\"" + expressNo + "\",\"expressMsg\":" + data + "}";

        //去除html空格
        String expressMsg = ret.replace("&nbsp;", "");
        return expressMsg;

    }

    private static MessageDigest getMdInst() {
        if (_mdInst == null) {
            try {
                _mdInst = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return _mdInst;
    }

    public static String encode(String s) {
        try {
            byte[] btInput = s.getBytes();
            // 使用指定的字节更新摘要
            getMdInst().update(btInput);
            // 获得密文
            byte[] md = getMdInst().digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public static void main(String[] args) {
           String expressNo = "882092636128449688";//快递单号
        String expressCode = "yuantong";//快递公司code
        String expressName = "圆通速递";//快递公司名称
        String key = "QRELChcD2660";//快递100授权码
        String customer ="F9231AE74659C23B5FC9BEE848024E79"; //快递100分配的的公司编号
        String expressMessage = getExpressMessageByOrder(expressNo, expressCode, expressName, key, customer, "2016-08-24 15:35:54");
        System.out.println("【" + expressMessage+ "】");

	}*/
}
