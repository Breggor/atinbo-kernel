package com.atinbo.core.http.model;

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
     * 起始行
     */
    private Integer offset;

    /**
     * 每页行数
     */
    private Integer limit;
}
