package com.atinbo.utils;

import com.atinbo.model.PageParam;
import com.atinbo.model.Pagination;
import com.atinbo.model.SortDirection;
import com.atinbo.model.SortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zenghao
 * @date 2019-10-26
 */
public class PageUtil {

    public static PageRequest toPageRequest(PageParam pageParam){
        Sort sort = toSort(pageParam.getSort());
        return PageRequest.of(pageParam.getPage(), pageParam.getSize(), sort);
    }

    public static PageRequest toPageRequest(PageParam pageParam, Sort defaultSort){
        Sort sort = toSort(pageParam.getSort());
        return PageRequest.of(pageParam.getPage(), pageParam.getSize(), sort.isSorted() ? sort : defaultSort);
    }

    public static Sort toSort(SortInfo sortInfo){
        if(sortInfo != null && !sortInfo.isEmpty()) {
            List<String> ascFields = sortInfo.get(SortDirection.ASC);
            List<String> descFields = sortInfo.get(SortDirection.DESC);

            List<Sort.Order> orders = new ArrayList<>();

            ascFields.forEach(field -> orders.add(Sort.Order.asc(field)));
            descFields.forEach(field -> orders.add(Sort.Order.desc(field)));
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    public static Pagination toPagination(Page page){
        return Pagination.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}