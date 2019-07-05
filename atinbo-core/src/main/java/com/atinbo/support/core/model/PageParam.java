package com.atinbo.support.core.model;

import java.io.Serializable;

/**
 * 分页传入参数
 */
public class PageParam implements Serializable {
    /**
     * 默认单页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认当前页
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;
    private static final long serialVersionUID = 9181509197952826120L;
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

    public PageParam() {
    }

    /**
     * @param pageSize    单页记录数
     * @param currentPage 当前页
     */
    public PageParam(int pageSize, int currentPage) {
        if (pageSize <= 0) {
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageParam{");
        sb.append("currentPage=").append(currentPage);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", offset=").append(offset);
        sb.append('}');
        return sb.toString();
    }
}
