package com.atinbo.model;

import java.io.Serializable;
import java.util.StringJoiner;


/**
 * 分页传入参数
 *
 * @author breggor
 */
public class PageParam implements Serializable {
    /**
     * 默认单页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 最大分页行数
     */
    public static final int MAX_PAGE_SIZE = 1000;
    /**
     * 默认当前页
     */
    public static final int DEFAULT_CURRENT_PAGE = 0;

    /**
     * 当前页
     */
    private int page;
    /**
     * 每页行数
     */
    private int size = 10;
    /**
     * 排序
     */
    private SortInfo sort;

    public PageParam() {
        super();
    }

    public PageParam(int page, int size) {
        this(page, size, null);
    }

    public PageParam(int page, int size, SortInfo sort) {
        this.page = (page <= 0) ? DEFAULT_CURRENT_PAGE : page;
        this.size = (size <= 0 || size >= MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : size;
        this.sort = sort;
    }

    /**
     * 创建
     *
     * @param page 当前页
     * @param size 每页行数
     * @return
     */
    public static PageParam of(int page, int size) {
        return new PageParam(page, size);
    }


    /**
     * 创建
     *
     * @param page 当前页
     * @param size 每页行数
     * @param sort 排序
     * @return
     */
    public static PageParam of(int page, int size, SortInfo sort) {
        return new PageParam(page, size, sort);
    }


    /**
     * 起始行
     *
     * @return
     */
    public int getOffset() {
        return getPage() * getSize();
    }

    public int getPage() {
        return page <= 0 ? DEFAULT_CURRENT_PAGE : page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        if (size <= 0 || size >= MAX_PAGE_SIZE) {
            size = DEFAULT_PAGE_SIZE;
        }
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SortInfo getSort() {
        return sort;
    }

    public void setSort(SortInfo sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PageParam.class.getSimpleName() + "[", "]")
                .add("page=" + page)
                .add("size=" + size)
                .add("offset=" + getOffset())
                .add("sort=" + sort)
                .toString();
    }
}