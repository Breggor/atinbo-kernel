package com.atinbo.generate.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zenghao
 * @date 2019-11-11
 */
@Data
@Accessors(chain = true)
public class GenForm implements Serializable {

    private String tableName;

    /**
     * 作者
     */
    private String author;

    /**
     * 项目包名
     */
    private String packageName;

    /**
     * 生成类型  hibernate 或者 mybatis
     */
    private String category;

    /**
     * 生成框架  dubbo 或者 springCloud
     */
    private String framework;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    private String tablePrefix;
}
