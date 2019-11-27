package com.atinbo.webmvc.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.atinbo.common.sql.SqlKeywords;
import com.atinbo.core.resolver.PageResolver;
import com.atinbo.core.utils.ObjectUtil;
import com.atinbo.model.Outcome;
import com.atinbo.model.Pagable;
import com.atinbo.model.PageForm;
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
        if (page != null && !ObjectUtil.isEmpty(page.getPage()) && !ObjectUtil.isEmpty(page.getSize())) {
            String orderBy = SqlKeywords.escapeOrderBySql(page.getSortBy());
            PageHelper.startPage(page.getPage(), page.getSize(), orderBy);
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
        return Outcome.success(Pagable.of(page.getPageNum(), page.getPageSize(), page.getTotal(), list));
    }

}
