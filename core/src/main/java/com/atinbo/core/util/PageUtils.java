package com.atinbo.core.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 分页工具类
 *
 * @author Administrator
 */
public class PageUtils {
    // private static Logger log = Logger.getLogger(PageUtils.class);

    /**
     * 分页获取当前页
     *
     * @param request
     * @return
     */
    public static int getCurrentPageNum(HttpServletRequest request) {
        String offsetStr = request.getParameter("offset");
        String limitStr = request.getParameter("limit");
        int offset = 0;
        int rows = 10;
        if (StringUtils.hasLength(offsetStr)) {
            offset = Integer.valueOf(offsetStr);
        }
        if (StringUtils.hasLength(limitStr)) {
            rows = Integer.valueOf(limitStr);
        }
        // 分页处理
        int currentPage = 1;
        if (offset != 0) {
            currentPage = offset / rows + 1;
        }
        return currentPage;
    }

    /**
     * 获取每页显示的条数
     *
     * @param request
     * @return
     */
    public static int getPageRows(HttpServletRequest request) {
        String limitStr = request.getParameter("limit");
        int rows = 10;
        if (StringUtils.hasLength(limitStr)) {
            rows = Integer.valueOf(limitStr);
        }
        return rows;
    }
}
