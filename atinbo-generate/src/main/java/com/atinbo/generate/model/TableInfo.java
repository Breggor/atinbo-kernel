package com.atinbo.generate.model;

import lombok.Data;

import java.util.Date;

/**
 * 数据库表信息
 * @author code-generator
 * @date 2019-8-20
 */
@Data
public class TableInfo {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;
}
