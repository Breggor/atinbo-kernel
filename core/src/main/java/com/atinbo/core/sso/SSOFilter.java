package com.atinbo.core.sso;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * SSO过滤器
 *
 * @author Administrator
 */
public abstract class SSOFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(SSOFilter.class);


    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sRequest;
        HttpServletResponse response = (HttpServletResponse) sResponse;
        String url = request.getRequestURL().toString();

        //2.判断是否是登录接口
        if (url.contains("/") || url.contains("login") || url.contains("syncEc")) {//是，直接放行
            chain.doFilter(request, response);
        } else {
            //3.判断是否带登录cookie，没有带，向浏览器发送跳转登录页cookie
            if (!checkLoginCookie(request)) {
                this.writeCookie(request, response);
                return;
            }


            boolean layout = doValidLogin(request, response);//userService.isLogin(uId);
            //未登录，向浏览器发送跳转登录页cookie
            if (!layout) {
                this.writeCookie(request, response);
                return;
            }
            //已登录，放行
            chain.doFilter(request, response);
        }
    }

    protected abstract boolean doValidLogin(HttpServletRequest request, HttpServletResponse response);


    public Integer getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String value = "0";
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("uId".equals(cookie.getName())) {
                value = cookie.getValue();
                break;
            }
        }
        return Integer.valueOf(value);
    }

    public void writeCookie(HttpServletRequest request, HttpServletResponse response) {
        //创建Cookie，存放标记
        Cookie loginCookie = new Cookie("layout", "0");
        //设置域
        String referer = request.getHeader("Referer");
        log.info("SSOFilter中获取的Referer={}", referer);
        if (StringUtils.isNotBlank(referer)) {
            try {
                URL url = new URL(referer);
                if (!url.getHost().contains("127.0.0.1") && !url.getHost().contains("localhost") && url.getHost().contains(".")) {
                    String domain = url.getHost().substring(url.getHost().indexOf("."));
                    log.info("SSOFilter设置退出登录cookie的域domain={}", domain);
                    loginCookie.setDomain(domain);
                    //设置有效路径
                    loginCookie.setPath("/");
                    //3：将Cookie存放到response对象
                    response.addCookie(loginCookie);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                log.info("{}不是正常URL", referer);
            }
        }

    }

    /**
     * @param @param  request
     * @param @return
     * @return boolean
     * @throws
     */
    public boolean checkLoginCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("loginId".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param @param  request
     * @param @return
     * @return String
     * @throws
     */
    public String getLoginCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("loginId".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
