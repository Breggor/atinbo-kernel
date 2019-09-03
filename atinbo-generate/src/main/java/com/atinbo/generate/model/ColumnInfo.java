package com.atinbo.generate.model;

import lombok.Data;

/**
 * 数据库列信息
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Data
public class ColumnInfo {

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 索引类型
     */
    private String columnKey;
}
