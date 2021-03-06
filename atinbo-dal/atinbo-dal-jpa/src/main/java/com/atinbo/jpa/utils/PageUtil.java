package com.atinbo.jpa.utils;

import com.atinbo.model.Pagable;
import com.atinbo.model.PageParam;
import com.atinbo.model.SortDir;
import com.atinbo.model.SortInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zenghao
 * @date 2019-10-26
 */
public class PageUtil {

    public static PageRequest toPageRequest(PageParam pageParam) {
        //默认不排序
        return toPageRequest(pageParam, Sort.unsorted());
    }

    public static PageRequest toPageRequest(PageParam pageParam, Sort defaultSort) {
        Sort sort = toSort(pageParam.getSort());
        //JPA 页码是从0开始，此处需要减去1
        return PageRequest.of(pageParam.getPage() - 1, pageParam.getSize(), sort.isSorted() ? sort : defaultSort);
    }

    public static Sort toSort(SortInfo sortInfo) {
        if (sortInfo != null && !sortInfo.isEmpty()) {
            List<String> ascFields = sortInfo.get(SortDir.ASC);
            List<String> descFields = sortInfo.get(SortDir.DESC);

            List<Sort.Order> orders = new ArrayList<>();

            ascFields.forEach(field -> orders.add(Sort.Order.asc(field)));
            descFields.forEach(field -> orders.add(Sort.Order.desc(field)));
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    public static Pagable toPageable(org.springframework.data.domain.Page page) {
        return Pagable.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }
}
