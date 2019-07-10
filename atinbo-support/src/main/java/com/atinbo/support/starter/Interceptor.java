//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Interceptor {
    protected Interceptor prev;
    protected Interceptor next;

    public Interceptor() {
    }

    public abstract void before(HttpServletRequest var1, HttpServletResponse var2, HandlerMethod var3);

    public abstract void after(HttpServletRequest var1, HttpServletResponse var2, HandlerMethod var3, ModelAndView var4);
}
