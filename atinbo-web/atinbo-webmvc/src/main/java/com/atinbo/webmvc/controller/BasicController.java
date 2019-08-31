package com.atinbo.webmvc.controller;

import com.atinbo.core.exception.HttpAPIException;
import com.atinbo.core.http.status.HttpStatusCode;
import com.atinbo.model.GatewayUser;
import com.atinbo.webmvc.exceptions.UserNotFoundException;
import com.atinbo.webmvc.resolver.SessionUserResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础控制类
 *
 * @author breggor
 */
@Slf4j
public class BasicController {
    private static final String ENV_STRICT_KEY = "strict_mode";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;


    protected GatewayUser getSessionUser() throws HttpAPIException {
        return this.getSessionUser(this.request);
    }

    protected GatewayUser getSessionUser(GatewayUser defaultUser) throws HttpAPIException {
        return this.getSessionUser(this.request, defaultUser);
    }

    protected GatewayUser getSessionUser(HttpServletRequest request) throws HttpAPIException {
        return this.getSessionUser(request, GatewayUser.ANONYMOUS);
    }

    protected GatewayUser getSessionUser(HttpServletRequest request, GatewayUser defaultUser) throws HttpAPIException {
        GatewayUser user = null;
        try {
            user = SessionUserResolver.getSessionUser(request);
        } catch (UserNotFoundException ex) {
            //环境变量获取参数：是否开启默认用户
            String strictEvnValue = System.getenv(ENV_STRICT_KEY);
            boolean isStrictMode = !StringUtils.isEmpty(strictEvnValue);
            if (isStrictMode) {
                throw new HttpAPIException(HttpStatusCode.ERR_401);
            }
            user = defaultUser;
        }
        user.setOperatorId(1L);
        return user;
    }
}
