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

    public static final String MODULE_API = "api";
    public static final String MODULE_SERVICE = "service";
    public static final String MODULE_OPENAPI = "openapi";

    /**
     * 数据库类型和java类型映射
     */
    private static Map<String, String> TYPE_MAPPING = new HashMap<>();

    static {
        TYPE_MAPPING.put("INT" , "Integer");
        TYPE_MAPPING.put("TINYINT" , "Integer");
        TYPE_MAPPING.put("SMALLINT" , "Integer");
        TYPE_MAPPING.put("BIGINT" , "Long");
        TYPE_MAPPING.put("FLOAT" , "Float");
        TYPE_MAPPING.put("DOUBLE" , "Double");
        TYPE_MAPPING.put("DATETIME" , "LocalDateTime");
        TYPE_MAPPING.put("TIMESTAMP" , "LocalDateTime");
        TYPE_MAPPING.put("BIT" , "Boolean");
        TYPE_MAPPING.put("DATE" , "LocalDate");
        TYPE_MAPPING.put("TIME" , "LocalTime");
        TYPE_MAPPING.put("VARCHAR" , "String");
        TYPE_MAPPING.put("TEXT" , "String");
        TYPE_MAPPING.put("MEDIUMTEXT" , "String");
        TYPE_MAPPING.put("LONGTEXT" , "String");
        TYPE_MAPPING.put("CHAR" , "String");
        TYPE_MAPPING.put("BLOB" , "String");
        TYPE_MAPPING.put("DECIMAL" , "BigDecimal");
    }

    public static String getJavaClass(String dbType) {
        if (StringUtils.isNotBlank(dbType) && TYPE_MAPPING.containsKey(dbType.toUpperCase())) {
            return TYPE_MAPPING.get(dbType.toUpperCase());
        }
        throw new RuntimeException(String.format("cannot found javaType mapping for dbType: %s ,please add to GenerateUtil.TYPE_MAPPING" , dbType));
    }

    public static String getPackageName(String packageName, String moduleName) {
        if (packageName == null || moduleName == null || moduleName.trim().length() == 0 || packageName.endsWith(moduleName)) {
            return packageName;
        }
        return packageName.concat(".").concat(moduleName.replace("-" , "."));
    }

    /**
     * 生成文件路径
     *
     * @param outPath     配置输出路径
     * @param packageName 包名
     * @return
     */
    public static String genFilePath(String outPath, String packageName) {
        return outPath + File.separator + packageName.replace("." , File.separator);
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
