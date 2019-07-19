package com.atinbo.mvc.controller;

import com.atinbo.core.exception.HttpAPIException;
import com.atinbo.core.http.status.HttpStatusCode;
import com.atinbo.mvc.resolver.SessionUserResolver;
import com.atinbo.mvc.resolver.UserNotFoundException;
import com.atinbo.mvc.user.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BasicController {
    private static final String STRICT_EVN_KEY = "STRICT";
    private static final String STRICT_EVN_VALUE = "ATINBO";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;


    protected SessionUser getSessionUserFromGateway() throws HttpAPIException {
        return this.getSessionUserFromGateway(this.request);
    }

    protected SessionUser getSessionUserFromGateway(SessionUser defaultSessionUser) throws HttpAPIException {
        return this.getSessionUserFromGateway(this.request, defaultSessionUser);
    }

    protected SessionUser getSessionUserFromGateway(HttpServletRequest request) throws HttpAPIException {
        return this.getSessionUserFromGateway(request, SessionUser.ANONYMOUS);
    }

    protected SessionUser getSessionUserFromGateway(HttpServletRequest request, SessionUser defaultSessionUser) throws HttpAPIException {
        SessionUser sessionUserFromGateway = null;

        try {
            sessionUserFromGateway = SessionUserResolver.getSessionUserFromGateway(request);
        } catch (UserNotFoundException ex) {
            String strictEvnValue = System.getenv(STRICT_EVN_KEY);
            boolean isStrictMode = !StringUtils.isEmpty(strictEvnValue) && STRICT_EVN_VALUE.equalsIgnoreCase(strictEvnValue);
            if (isStrictMode) {
                throw new HttpAPIException(HttpStatusCode.ERR_401);
            }

            sessionUserFromGateway = defaultSessionUser;
        }
        //TODO 设置操作人ID
        sessionUserFromGateway.setOperatorId(1L);
        return sessionUserFromGateway;
    }
}
