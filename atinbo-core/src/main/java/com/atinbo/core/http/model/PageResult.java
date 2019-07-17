package com.atinbo.core.http.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页封装类
 *
 * @param <T>
 * @author Breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageResult<T extends BaseVO> implements Serializable {

    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 页总数
     */
    private Integer totalPage;
    /**
     * 单页记录数
     */
    private Integer pageSize;
    /**
     * 总行数
     */
    private Integer totalCount;
    /**
     * 分页数据列表
     */
    private List<T> data;

    /**
     * @param currentPage 当前页
     * @param totalPage   总页数
     * @param pageSize    每个行数
     * @param totalCount
     * @param data
     * @param <E>
     * @return
     */
    public static <E> PageResult of(int currentPage, int totalPage, int pageSize, int totalCount, List<E> data) {
        return new PageResult(currentPage, totalPage, pageSize, totalCount, data);
    }
}
