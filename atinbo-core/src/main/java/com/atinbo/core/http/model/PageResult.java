package com.atinbo.core.http.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页封装类
 *
 * @param <T>
 * @author Breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageResult<T extends BaseVO> implements Serializable {
    /**
     * 分页数据列表
     */
    private List<T> data;

    /**
     * 总行数
     */
    private Integer totalCount;
    /**
     * 页总数
     */
    private Integer totalPage;
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 单页记录数
     */
    private Integer pageSize;
}
