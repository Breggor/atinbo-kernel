package com.atinbo.mvc.interceptor;

import com.atinbo.core.http.model.ErrResult;
import com.atinbo.core.http.status.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParamValidateInterceptor implements HandlerInterceptor {

    protected static final Logger logger = LoggerFactory.getLogger(ParamValidateInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        StringBuilder errs = new StringBuilder();
        if (!checkBusinessParam(request, handler, errs)) {
            ErrResult resultVO = ErrResult.error(HttpStatusCode.ERR_400.getHttpCode(), errs.toString());
            String resultString = resultVO.toString();
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resultString);
            logger.warn(resultString);
            return false;
        }

        return true;
    }

    private boolean checkBusinessParam(HttpServletRequest request, Object handler,
                                       StringBuilder sBuiler) {
        boolean hasNull = false;
        HandlerMethod method = (HandlerMethod) handler;
        //RequestMapping requestMapping = method.getMethodAnnotation(RequestMapping.class);
        MethodParameter[] methodParams = method.getMethodParameters();
        for (MethodParameter param : methodParams) {
            org.springframework.web.bind.annotation.RequestParam requestParam =
                    param.getParameterAnnotation(org.springframework.web.bind.annotation.RequestParam.class);
            if (null != requestParam && requestParam.required()) {
                // the parameter cannot null.
                // so, build JSON in here.
                if (StringUtils.isEmpty(request.getParameter(requestParam.value()))) {
                    String format = "Parameters (%s) cannot be null!";
                    sBuiler.append(String.format(format, requestParam.value()));
                    hasNull = true;
                    break;
                }
            }
        }

        //if hasNull -> response.getWriter().write(sbString);
        return !hasNull;
    }
}
