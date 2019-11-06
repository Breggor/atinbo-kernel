package com.atinbo.mybatis.utils;

import com.atinbo.model.PageParam;
import com.atinbo.model.Pagination;
import com.atinbo.model.SortDirection;
import com.atinbo.model.SortInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * @author zenghao
 * @date 2019-11-06
 */
public class PageUtil {

    /**
     * 分页参数转换
     * @param pageParam 分页参数
     * @return
     */
    public static <T> Page<T> toPage(PageParam pageParam){
        return toPage(pageParam, null);
    }

    /**
     * 分页参数转换
     * @param pageParam 分页参数
     * @param defaultSort 默认排序
     * @return
     */
    public static <T> Page<T> toPage(PageParam pageParam, SortInfo defaultSort){
        Page<T> page = new Page<T>(pageParam.getPage(), pageParam.getSize());

        SortInfo sortInfo = ObjectUtils.defaultIfNull(pageParam.getSort(), defaultSort);
        if(sortInfo != null && !sortInfo.isEmpty()) {
            List<String> ascFields = sortInfo.get(SortDirection.ASC);
            List<String> descFields = sortInfo.get(SortDirection.DESC);

            ascFields.forEach(field -> page.addOrder(OrderItem.asc(field)));
            descFields.forEach(field -> page.addOrder(OrderItem.desc(field)));
        }
        return page;
    }

    /**
     * 分页结果转换
     * @param page
     * @return
     */
    public static Pagination toPagination(IPage page){
        return Pagination.of(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages());
    }
}
