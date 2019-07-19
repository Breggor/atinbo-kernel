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

    public static final PageInfo EMPTY = new PageInfo(0, 10, 0, 0);

    /**
     * 当前页
     */
    private int page;
    /**
     * 每页行数
     */
    private int size;
    /**
     * 总页数
     */
    private int total;
    /**
     * 总行数
     */
    private long rows;

    /**
     * 创建分页
     *
     * @param page  当前页
     * @param size  每页行数
     * @param total 总页数
     * @param rows  总行数
     * @return
     */
    public static PageInfo of(int page, int size, int total, long rows) {
        return new PageInfo(page, size, total, rows);
    }
}