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


    /**
     * 状态[0:未删除,1:删除]
     */
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
}
