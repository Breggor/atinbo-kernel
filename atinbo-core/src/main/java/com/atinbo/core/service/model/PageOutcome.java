package com.atinbo.core.service.model;

import com.atinbo.core.model.Pagination;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Accessors(chain = true)
public class PageOutcome<T extends BaseBO> implements Serializable {

    /**
     * 分页信息
     */
    private Pagination page = Pagination.EMPTY;
    /**
     * 分页数据列表
     */
    private List<T> data;

    /**
     * 是否成功
     */
    private boolean success = false;
    /**
     * 错误信息
     */
    private String error;

    /**
     * 失败返回结果
     *
     * @param error
     * @param <E>
     * @return
     */
    public static <E extends BaseBO> PageOutcome<E> ofFail(String error) {
        return new PageOutcome<E>().setError(error);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @param <E>
     * @return
     */
    public static <E extends BaseBO> PageOutcome<E> ofSuccess(Pagination page, List<E> data) {
        return new PageOutcome<E>().setSuccess(true).setPage(page).setData(data);
    }
}