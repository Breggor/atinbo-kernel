package com.atinbo.core.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 分页封装类
 *
 * @param <T>
 * @author Breggor
 */
public class PageVO<T> implements Serializable {
    public static final int VIEW_STRATEGY_ROWS = 1;
    public static final int VIEW_STRATEGY_DATA = 0;
    /**
     * 默认单页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认当前页
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;
    private static final long serialVersionUID = 1316462925566112644L;
    /**
     * 显示策略：0）显示data字段，1）显示rows字段
     * 默认：显示data
     */
    private static volatile int VIEW_STRATEGY = 0;
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


    public PageVO() {
    }

    /**
     * 构造函数
     *
     * @param pageSize    单页记录数
     * @param currentPage 当前页
     * @param totalCount  总记录数
     */
    public PageVO(int pageSize, int currentPage, int totalCount) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = DEFAULT_CURRENT_PAGE;
        }
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.totalPage = (totalCount - 1) / pageSize + 1;

    }

    /**
     * solr分页时的构造函数
     *
     * @param pageSize    单页记录数
     * @param currentPage 当前页
     */
    public PageVO(int pageSize, int currentPage) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = DEFAULT_CURRENT_PAGE;
        }
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        if (currentPage > totalPage) {
            this.currentPage = totalPage;
        }
    }

    public static void setViewStrategy(int viewStrategy) {
        VIEW_STRATEGY = viewStrategy;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
//        this.totalPage = (totalCount - 1) / pageSize + 1;
    }

    public List<T> getData() {
        if (VIEW_STRATEGY == VIEW_STRATEGY_DATA) {
            return data;
        }
        return null;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public List<T> getRows() {
        if (VIEW_STRATEGY == VIEW_STRATEGY_ROWS) {
            return this.data;
        }
        return null;
    }

    public void setRows(List<T> rows) {
        if (rows != null && !rows.isEmpty()) {
            setData(rows);
        }
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
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

    public Integer getTotal() {
        return this.getTotalCount();
    }

    public void setTotal(int total) {
        if (total > 0)
            setTotalCount(total);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("data", data)
                .append("totalCount", totalCount)
                .append("totalPage", totalPage)
                .append("currentPage", currentPage)
                .append("pageSize", pageSize)
                .toString();
    }
}
