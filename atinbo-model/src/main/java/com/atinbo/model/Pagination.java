package com.atinbo.model;

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
public final class Pagination implements Serializable {

    public static final Pagination EMPTY = new Pagination(0, 10, 0, 0);

    /**
     * 当前页
     */
    private int current;
    /**
     * 每页行数
     */
    private int size;
    /**
     * 总行数
     */
    private long totalRows;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 创建分页
     *
     * @param current    当前页
     * @param size       每页行数
     * @param totalRows  总行数
     * @param totalPages 总页数
     * @return
     */
    public static Pagination of(int current, int size, long totalRows, int totalPages) {
        return new Pagination(current, size, totalRows, totalPages);
    }
}