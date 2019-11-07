package com.atinbo.core.resolver;


import com.atinbo.core.http.model.PageForm;
import com.atinbo.core.utils.ServletUtils;

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
        form.setOffset(ServletUtils.getParameterToInt(PAGE_NUM));
        form.setLimit(ServletUtils.getParameterToInt(PAGE_SIZE));
        form.setOrderBy(ServletUtils.getParameter(ORDER_BY));
        form.setDir(ServletUtils.getParameter(DIRECTION));
        return form;
    }
}
