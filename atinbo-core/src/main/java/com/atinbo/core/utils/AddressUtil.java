package com.atinbo.core.utils;


import com.atinbo.common.http.HttpUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author breggor
 */
public class AddressUtil {
    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";
    private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);


    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }

        JsonObject obj = new JsonParser().parse(rspStr).getAsJsonObject();
        JsonObject data = obj.getAsJsonObject("data");
        String region = data.get("region").getAsString();
        String city = data.get("city").getAsString();
        address = region + " " + city;
        return address;
    }
}
