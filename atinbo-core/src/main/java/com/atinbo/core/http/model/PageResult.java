package com.atinbo.core.http.model;

import com.atinbo.core.model.PageInfo;
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
public class PageResult<T extends BaseVO> extends PageInfo implements Serializable {
    /**
     * 分页数据列表
     */
    private List<T> data;

    public PageResult(PageInfo page, List<T> data) {
        super(page.getPage(), page.getSize(), page.getTotal(), page.getRows());
        this.data = data;
    }

    /**
     * @param page
     * @param data
     * @param <E>
     * @return
     */
    public static <E> PageResult of(PageInfo page, List<E> data) {
        return new PageResult(page, data);
    }
}
