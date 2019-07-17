//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


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

    private String extras;
    private String reqId;
    private Integer sort;
    private Integer version;
}
