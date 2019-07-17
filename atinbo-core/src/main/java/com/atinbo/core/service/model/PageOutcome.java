package com.atinbo.core.service.model;

import com.atinbo.core.base.PageInfo;
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
    private PageInfo page = PageInfo.EMPTY;
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
     * 构造函数
     *
     * @param pageSize    每页行数
     * @param currentPage 当前页
     * @param totalCount  总行数
     */
    public PageOutcome(int pageSize, int currentPage, int totalCount) {
        if (pageSize <= 0) {
            pageSize = PageInfo.DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = PageInfo.DEFAULT_CURRENT_PAGE;
        }
        page.setPageSize(pageSize);
        page.setCurrentPage(currentPage);
        page.setTotalCount(totalCount);
        page.setTotalPage((totalCount - 1) / pageSize + 1);

    }

    /**
     * solr分页时的构造函数
     *
     * @param pageSize    单页记录数
     * @param currentPage 当前页
     */
    public PageOutcome(int pageSize, int currentPage) {
        if (pageSize <= 0) {
            pageSize = PageInfo.DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = PageInfo.DEFAULT_CURRENT_PAGE;
        }
        page.setPageSize(pageSize);
        page.setCurrentPage(currentPage);

        if (currentPage > page.getTotalPage()) {
            page.setCurrentPage(page.getTotalPage());
        }
    }


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
    public static <E extends BaseBO> PageOutcome<E> ofSuccess(PageInfo page, List<E> data) {
        return new PageOutcome<E>().setSuccess(true).setPage(page).setData(data);
    }
}