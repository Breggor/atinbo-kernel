package com.atinbo.core.service.model;

import com.atinbo.core.base.SortInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.atinbo.core.constants.CoreConstants.*;

/**
 * 分页传入参数
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页行数
     */
    private Integer pageSize;
    /**
     * 起始行
     */
    private Integer offset;
    /**
     * 排序
     */
    private SortInfo sort;

    /**
     * @param currentPage 当前页
     * @param pageSize    单页记录数
     */
    public PageParam(int currentPage, int pageSize) {
        if (pageSize <= 0 || pageSize >= MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = DEFAULT_CURRENT_PAGE;
        }
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.offset = pageSize * (currentPage - 1);
    }

    /**
     * @param offset   起始行
     * @param pageSize 每页行数
     */
    public static PageParam create(int offset, int pageSize) {
        offset = (offset < 0) ? 0 : offset;
        pageSize = (pageSize < 0) ? 0 : pageSize;
        int currentPage = offset / pageSize + 1;
        PageParam pageParam = new PageParam(pageSize, currentPage);
        return pageParam;
    }
}
