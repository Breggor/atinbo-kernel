package com.atinbo.webmvc.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json与POJO转换，忽略没用字段
 *
 * @author breggor
 */
public class JsonIgnoreFieldObjectMapper extends ObjectMapper {
    public JsonIgnoreFieldObjectMapper() {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
