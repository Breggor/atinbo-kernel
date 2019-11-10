package com.atinbo.common.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


/**
 * JacksonUtils
 *
 * @author breggor
 */
public class Jacksons {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //默认SerializationFeature.FAIL_ON_EMPTY_BEANS为true,实体不提供getter、setter方法时报错
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//		mapper.setDateFormat(new SimpleDateFormat(DateUtil.defaultFormat));
    }

    public static <T> T parseToObject(InputStream is, Class<T> toClass) {
        try {
            return (T) mapper.readValue(is, toClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseToObject(byte[] b, int offset, int len, Class<T> valueType) {
        try {
            return (T) mapper.readValue(b, offset, len, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseToObject(String json, Class<T> toClass) {
        try {
            if (json == null || "".equals(json)) {
                return null;
            }
            return (T) mapper.readValue(json, toClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<?, ?> parseToMap(String json) {
        return parseToObject(json, Map.class);
    }

    public static Map<?, ?> parseToMap(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        return parseToObject(b, 0, b.length, Map.class);
    }

    public static Map<?, ?> parseToMap(InputStream is) {
        return parseToObject(is, Map.class);
    }

    public static String parseToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @return JSON字符串
     */
    public static String serialize(Object object) {
        Writer write = new StringWriter();
        try {
            mapper.writeValue(write, object);
        } catch (JsonGenerationException e) {
            throw new RuntimeException("JsonGenerationException when serialize object to json" , e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("JsonMappingException when serialize object to json" , e);
        } catch (IOException e) {
            throw new RuntimeException("IOException when serialize object to json" , e);
        }
        return write.toString();
    }


    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;
        try {
            object = mapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonParseException e) {
            throw new RuntimeException("JsonParseException when serialize object to json" , e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("JsonMappingException when serialize object to json" , e);
        } catch (IOException e) {
            throw new RuntimeException("IOException when serialize object to json" , e);
        }

        return (T) object;
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, TypeReference<T> typeRef) {
        try {
            //TODO: warn
            return (T) mapper.readValue(json, typeRef);
        } catch (JsonParseException e) {
            throw new RuntimeException("JsonParseException when deserialize json");
        } catch (JsonMappingException e) {
            throw new RuntimeException("JsonParseException when deserialize json");
        } catch (IOException e) {
            throw new RuntimeException("JsonParseException when deserialize json");
        }
    }
}