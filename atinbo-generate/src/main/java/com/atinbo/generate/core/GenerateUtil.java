package com.atinbo.generate.core;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zenghao
 * @date 2019-09-02
 */
public class GenerateUtil {

    /**
     * 数据库类型和java类型映射
     */
    private static Map<String, String> TYPE_MAPPING = new HashMap<>();

    static {
        TYPE_MAPPING.put("INT", "Integer");
        TYPE_MAPPING.put("TINYINT", "Integer");
        TYPE_MAPPING.put("SMALLINT", "Integer");
        TYPE_MAPPING.put("BIGINT", "Long");
        TYPE_MAPPING.put("FLOAT", "Float");
        TYPE_MAPPING.put("DOUBLE", "Double");
        TYPE_MAPPING.put("DATETIME", "Date");
        TYPE_MAPPING.put("TIMESTAMP", "Date");
        TYPE_MAPPING.put("DATE", "Date");
        TYPE_MAPPING.put("TIME", "Date");
        TYPE_MAPPING.put("VARCHAR", "String");
        TYPE_MAPPING.put("TEXT", "String");
        TYPE_MAPPING.put("MEDIUMTEXT", "String");
        TYPE_MAPPING.put("LONGTEXT", "String");
        TYPE_MAPPING.put("CHAR", "String");
        TYPE_MAPPING.put("BLOB", "String");
        TYPE_MAPPING.put("DECIMAL", "BigDecimal");
    }


    public static String[] SUPPORT_CATEGORY = {"mybatis", "hibernate"};

    public static String getJavaClass(String dbType) {
        if (StringUtils.isNotBlank(dbType) && TYPE_MAPPING.containsKey(dbType.toUpperCase())) {
            return TYPE_MAPPING.get(dbType.toUpperCase());
        }
        throw new RuntimeException(String.format("cannot found javaType mapping for dbType: %s ,please add to TYPE_MAPPING", dbType));
    }

    /**
     * 生成文件路径
     *
     * @param outPath     配置输出路径
     * @param packageName 包名
     * @return
     */
    public static String genFilePath(String outPath, String packageName) {
        return outPath + File.separator + packageName.replace(".", File.separator);
    }

    /**
     * 表名生成类名
     *
     * @param tableName
     * @return
     */
    public static String genClassName(String tableName) {
        return tableName.substring(0, 1).toUpperCase() + underlineToCamelCase(tableName.substring(1));
    }

    /**
     * 下划线，转换为驼峰式
     *
     * @param underscoreName
     * @return
     */
    public static String underlineToCamelCase(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.trim().length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }
}
