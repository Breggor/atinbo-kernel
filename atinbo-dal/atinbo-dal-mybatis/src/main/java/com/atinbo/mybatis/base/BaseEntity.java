package com.atinbo.mybatis.base;

import com.atinbo.entity.BaseDomain;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 基础实体类
 *
 * @author breggor
 */
@Data
public class BaseEntity extends BaseDomain {

    /** 1. 已删除 */
    public static final int DELETED = 1;
    /** 0. 未删除 */
    public static final int UN_DELETED = 0;

    /**
     * 创建者Id
     */
    @ApiModelProperty(value = "创建者Id")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者Id
     */
    @ApiModelProperty(value = "更新者Id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
