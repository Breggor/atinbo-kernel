package com.atinbo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页数据封装类
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "分页数据")
public final class Pagable<T> implements Serializable {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long current = 1;

    /**
     * 每页行数
     */
    @ApiModelProperty(value = "每页行数")
    private long size = 10;

    /**
     * 总行数
     */
    @ApiModelProperty(value = "总行数")
    private long total;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long pages;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<T> records = Collections.emptyList();

    public Pagable(long current, long size, long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = calcPages();
        this.records = records;
    }

    /**
     * 总页数
     */
    private long calcPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 创建分页
     *
     * @param current
     * @param size
     * @param total
     * @return
     */
    public static Pagable of(long current, long size, long total) {
        return new Pagable(current, size, total, Collections.emptyList());
    }

    /**
     * 创建分页
     *
     * @param current 当前页
     * @param size    每页行数
     * @param total   总行数
     * @param records 数据列表
     * @return
     */
    public static <T> Pagable<T> of(int current, int size, long total, List<T> records) {
        return new Pagable<>(current, size, total, records);
    }

    /**
     * 创建分页
     *
     * @param current 当前页
     * @param size    每页行数
     * @param total   总行数
     * @param records 数据列表
     * @return
     */
    public static <T> Pagable<T> of(long current, long size, long total, List<T> records) {
        return new Pagable(current, size, total, records);
    }
}