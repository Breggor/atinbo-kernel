package com.atinbo.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageInfo implements Serializable {
    public static final PageInfo EMPTY = new PageInfo(null, null, null, null);

    /**
     * 默认单页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认当前页
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 页总数
     */
    private Integer totalPage;
    /**
     * 单页记录数
     */
    private Integer pageSize;
    /**
     * 总行数
     */
    private Integer totalCount;

    public static PageInfo of(int currentPage, int totalPage, int pageSize, int totalCount) {
        return new PageInfo(currentPage, totalPage, pageSize, totalCount);
    }
}
