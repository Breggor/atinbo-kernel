package com.atinbo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 *
 * @author breggor
 */
@Setter
@Getter
public abstract class BaseEntity implements Serializable {
    protected static final long serialVersionUID = 1L;

    /**
     * 创建者ID
     */
    private Long createId;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者ID
     */
    private Long updateId;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据权限
     */
    private String dataScope;

    /**
     * 排序
     */
    private Long sortBy;
}