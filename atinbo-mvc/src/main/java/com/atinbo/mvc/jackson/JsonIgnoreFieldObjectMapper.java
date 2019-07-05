package com.atinbo.mvc.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json与java bean转换，忽略没用字段
 * Created by Breggor on 2016/8/22 23:31.
 */
public class JsonIgnoreFieldObjectMapper extends ObjectMapper {
    public JsonIgnoreFieldObjectMapper() {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
