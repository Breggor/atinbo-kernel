package com.atinbo.core.resolver;


import com.atinbo.core.model.PageForm;
import com.atinbo.core.utils.ServletUtil;

public abstract class PageResolver {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "offset";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "limit";

    /**
     * 排序列
     */
    public static final String ORDER_BY = "orderBy";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String DIRECTION = "dir";


    public static PageForm resolve() {
        PageForm form = new PageForm();
        form.setOffset(ServletUtil.getParameterToInt(PAGE_NUM));
        form.setLimit(ServletUtil.getParameterToInt(PAGE_SIZE));
        form.setSortBy(ServletUtil.getParameter(ORDER_BY));
        return form;
    }
}
