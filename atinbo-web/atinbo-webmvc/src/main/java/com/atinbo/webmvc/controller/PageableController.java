package com.atinbo.webmvc.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.atinbo.common.sql.SqlKeywords;
import com.atinbo.core.model.PageForm;
import com.atinbo.core.resolver.PageResolver;
import com.atinbo.core.utils.ObjectUtil;
import com.atinbo.model.Outcome;
import com.atinbo.model.Pagable;
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
        if (page != null && !ObjectUtil.isEmpty(page.getOffset()) && !ObjectUtil.isEmpty(page.getLimit())) {
            String orderBy = SqlKeywords.escapeOrderBySql(page.getSortBy());
            PageHelper.startPage(page.getOffset(), page.getLimit(), orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    protected Outcome endPage(List<?> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        PageInfo page = new PageInfo(list);
        return Outcome.success(Pagable.of(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), list));
    }

}
