package com.atinbo.redis.key;

/**
 * redis key生成器
 *
 * @author breggor
 */
public abstract class RedisKeyGenerator {
    private final static String DELIMITER = ":";

    public static String generate(String delimiter, String... fields) {
        String key = "";
        if (fields == null || fields.length < 1) {
            throw new IllegalArgumentException("fields can not be empty");
        }

        if (null == delimiter || "".equals(delimiter)) {
            delimiter = DELIMITER;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, len = fields.length; i < len; i++) {
                sb.append("%s").append(delimiter);
            }
            sb.deleteCharAt(sb.length() - 1);
            key = String.format(sb.toString(), fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
}