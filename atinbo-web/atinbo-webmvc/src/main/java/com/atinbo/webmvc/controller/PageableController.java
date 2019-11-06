package com.atinbo.webmvc.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.atinbo.common.ObjectUtils;
import com.atinbo.common.sql.SqlUtils;
import com.atinbo.core.http.model.PageForm;
import com.atinbo.core.http.model.ResultVO;
import com.atinbo.core.resolver.PageResolver;
import com.atinbo.model.Pagination;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 可分页控制类
 *
 * @author breggor
 */
@Slf4j
public abstract class PageableController {

    /**
     * 设置请求分页数据
     */
    protected void beginPage() {
        PageForm page = PageResolver.resolve();
        if (page != null && !ObjectUtils.isEmpty(page.getOffset()) && !ObjectUtils.isEmpty(page.getLimit())) {
            String orderBy = SqlUtils.escapeOrderBySql(page.getOrderBy());
            PageHelper.startPage(page.getOffset(), page.getLimit(), orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    protected ResultVO endPage(List<?> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        PageInfo page = new PageInfo(list);
        return ResultVO.of(Pagination.of(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages()), list);
    }

}
