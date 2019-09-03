package com.atinbo.generate.vo;

import lombok.Data;

/**
 * 属性信息
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Data
public class FieldInfo {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 属性类型
     */
    private String fieldClass;
    /**
     * 属性注释
     */
    private String fieldComment;
    /**
     * 是否为主键
     */
    private boolean primaryKey;

}
