package com.atinbo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 *
 * @author breggor
 */
@Data
public abstract class BaseDomain implements Serializable {
    protected static final long serialVersionUID = 1L;

    /**
     * 创建者Id
     */
    @ApiModelProperty(value = "创建者Id")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者Id
     */
    @ApiModelProperty(value = "更新者Id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 数据权限
     */
    @ApiModelProperty(value = "数据权限")
    private String dataScope;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long sortBy;

    /**
     * 状态[0:未删除,1:删除]
     */
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
}