package com.atinbo.core.domain;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 */
@Setter
@Getter
public abstract class BaseEntity implements Serializable {

    /**
     * 创建人与创建时间
     */
    private Long createdBy;
    private String createByName;
    private Date createAt;

    /**
     * 更新人与更新时间
     */
    private Long updatedBy;
    private String updatedByName;
    private Date updateAt;

    private Long sortBy;
    private Integer version;
}