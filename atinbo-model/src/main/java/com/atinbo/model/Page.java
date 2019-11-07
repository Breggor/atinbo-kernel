package com.atinbo.model;

import io.swagger.annotations.ApiModelProperty;
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
public final class Page implements Serializable {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long current;
    /**
     * 每页行数
     */
    @ApiModelProperty(value = "每页行数")
    private long size;
    /**
     * 总行数
     */
    @ApiModelProperty(value = "总行数")
    private long totalRows;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long totalPages;

    /**
     * 创建分页
     *
     * @param current    当前页
     * @param size       每页行数
     * @param totalRows  总行数
     * @param totalPages 总页数
     * @return
     */
    public static Page of(int current, int size, long totalRows, int totalPages) {
        return new Page(current, size, totalRows, totalPages);
    }


    /**
     * 创建分页
     *
     * @param current    当前页
     * @param size       每页行数
     * @param totalRows  总行数
     * @param totalPages 总页数
     * @return
     */
    public static Page of(long current, long size, long totalRows, long totalPages) {
        return new Page(current, size, totalRows, totalPages);
    }
}