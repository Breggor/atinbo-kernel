package com.atinbo.core.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 服务接口层返回对象
 *
 * @param <T>
 * @author breggor
 */
@Data
@Accessors(chain = true)
public class PageOutcome<T extends BaseBO> implements Serializable {
    /**
     * 默认单页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认当前页
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 分页数据列表
     */
    private List<T> data;

    /**
     * 总行数
     */
    private Integer totalRow;
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


    /**
     * 构造函数
     *
     * @param pageSize    每页行数
     * @param currentPage 当前页
     * @param totalRow    总行数
     */
    public PageOutcome(int pageSize, int currentPage, int totalRow) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = DEFAULT_CURRENT_PAGE;
        }
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRow = totalRow;
        this.totalPage = (totalRow - 1) / pageSize + 1;

    }

    /**
     * solr分页时的构造函数
     *
     * @param pageSize    单页记录数
     * @param currentPage 当前页
     */
    public PageOutcome(int pageSize, int currentPage) {
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
}