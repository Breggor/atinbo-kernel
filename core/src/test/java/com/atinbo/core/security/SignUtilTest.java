package com.atinbo.core.security;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SignUtilTest {

    @Test
    public void makeSignTest() throws Exception {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("platform", "2");
        reqMap.put("version", "5.3.10");
        reqMap.put("openId", "3A39BC5C685E2BAAC9A0DFCCC2AD7456");
        reqMap.put("accessToken", "ca59657df858356f2f2d0a37092992bb");
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        reqMap.put("timestamp", "1472903173150");
//		reqMap.put("paramName", "{id:5,name:kkk}");
        String sign = SignUtil.makeSign(reqMap, "d7c470ad-449d-4136-8cd9-ab78859135f4");
        System.out.println("sign: " + sign);
    }
}
