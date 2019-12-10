package com.atinbo.Interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc拦截
 *
 * @author breggor
 */
public abstract class Interceptor {

    protected Interceptor prev;

    protected Interceptor next;

    /**
     * 请求前处理
     *
     * @param request
     * @param response
     * @param method
     */
    public abstract void before(HttpServletRequest request, HttpServletResponse response, HandlerMethod method);

    /**
     * 请求后处理
     *
     * @param request
     * @param response
     * @param method
     * @param modelAndView
     */
    public abstract void after(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, ModelAndView modelAndView);
}
