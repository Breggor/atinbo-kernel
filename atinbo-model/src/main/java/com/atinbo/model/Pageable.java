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
 * 分页参数
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "分页数据")
public final class Pageable<T> implements Serializable {

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
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<T> records = Collections.emptyList();

    public Pageable(Pageable pageable, List<T> records) {
        this.current = pageable.getCurrent();
        this.size = pageable.getSize();
        this.totalPages = pageable.getTotalPages();
        this.totalRows = pageable.getTotalRows();
        this.records = records;
    }

    /**
     * 创建分页
     *
     * @param current    当前页
     * @param size       每页行数
     * @param totalRows  总行数
     * @param totalPages 总页数
     * @param records    数据列表
     * @return
     */
    public static <E> Pageable<E> of(int current, int size, long totalRows, int totalPages, List<E> records) {
        return new Pageable<E>(current, size, totalRows, totalPages, records);
    }


    /**
     * 创建分页
     *
     * @param current    当前页
     * @param size       每页行数
     * @param totalRows  总行数
     * @param totalPages 总页数
     * @param records    数据列表
     * @return
     */
    public static <E> Pageable<E> of(long current, long size, long totalRows, long totalPages, List<E> records) {
        return new Pageable(current, size, totalRows, totalPages, records);
    }


    /**
     * 创建分页
     *
     * @param pageable 分页
     * @param records  数据列表
     * @return
     */
    public static <E> Pageable<E> of(Pageable pageable, List<E> records) {
        return new Pageable(pageable, records);
    }
}