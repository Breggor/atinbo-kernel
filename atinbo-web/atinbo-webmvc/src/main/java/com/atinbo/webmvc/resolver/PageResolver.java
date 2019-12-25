package com.atinbo.webmvc.resolver;


import com.atinbo.model.PageForm;
import com.atinbo.webmvc.utils.WebUtil;

public abstract class PageResolver {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "page";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "size";

    /**
     * 排序列
     */
    public static final String SORT_BY = "sortBy";

    public static PageForm resolve() {
        PageForm form = new PageForm();
        form.setPage(WebUtil.getParameterToInt(PAGE_NUM));
        form.setSize(WebUtil.getParameterToInt(PAGE_SIZE));
        form.setSortBy(WebUtil.getParameter(SORT_BY));
        return form;
    }
}
