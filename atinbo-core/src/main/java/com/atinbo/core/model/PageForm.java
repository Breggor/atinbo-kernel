package com.atinbo.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页传入参数
 *
 * @author breggor
 */
@Data
public class PageForm implements Serializable {

    /**
     * 起始页
     */
    @ApiModelProperty(value = "起始页")
    private Integer offset;

    /**
     * 每页行数
     */
    @ApiModelProperty(value = "每页行数")
    private Integer limit;

    /**
     * 排序列
     */
    @ApiModelProperty(value = "+为正序[asc], -为反序[desc],多字段排序用','分隔，如: sortBy=+name,-age")
    private String sortBy;
}