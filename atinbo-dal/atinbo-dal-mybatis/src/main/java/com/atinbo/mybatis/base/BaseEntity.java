package com.atinbo.mybatis.base;


import com.atinbo.entity.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
     * 状态[0:未删除,1:删除]
     */
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
}
