//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import com.atinbo.support.starter.controller.BasicController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContextInterceptor extends Interceptor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RequestContextInterceptor() {
    }

    @Override
    public void before(HttpServletRequest request, HttpServletResponse response, HandlerMethod action) {
        Object bean = action.getBean();
        if (bean instanceof BasicController) {
            BasicController controller = (BasicController) bean;
            controller.setRequest(request);
            controller.setResponse(response);
        }

    }

    @Override
    public void after(HttpServletRequest request, HttpServletResponse response, HandlerMethod action, ModelAndView result) {
        System.out.println("");
    }
}
