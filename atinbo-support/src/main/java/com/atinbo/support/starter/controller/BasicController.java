//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter.controller;

import com.atinbo.core.exception.APIException;
import com.atinbo.support.error.Enum401Error;
import com.atinbo.support.exceptions.SessionUserNotFoundException;
import com.atinbo.support.starter.HttpRender;
import com.atinbo.support.starter.LoggerHelper;
import com.atinbo.support.user.SessionUser;
import com.atinbo.support.user.SessionUserResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Scope("prototype")
public class BasicController {
    private static final String STRICT_EVN_KEY = "STRICT";
    private static final String STRICT_EVN_VALUE = "ATINBO";
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String httpBody;

    public BasicController() {
    }

    protected SessionUser getSessionUserFromGateway() throws APIException {
        return this.getSessionUserFromGateway(this.request);
    }

    protected SessionUser getSessionUserFromGateway(SessionUser defaultSessionUser) throws APIException {
        return this.getSessionUserFromGateway(this.request, defaultSessionUser);
    }

    protected SessionUser getSessionUserFromGateway(HttpServletRequest request) throws APIException {
        SessionUser defaultSessionUser = new SessionUser();
        defaultSessionUser.setUserName("tester");
        defaultSessionUser.setUserId(-99L);
        return this.getSessionUserFromGateway(request, defaultSessionUser);
    }

    protected SessionUser getSessionUserFromGateway(HttpServletRequest request, SessionUser defaultSessionUser) throws APIException {
        SessionUser sessionUserFromGateway = null;

        try {
            sessionUserFromGateway = SessionUserResolver.getSessionUserFromGateway(request);
        } catch (SessionUserNotFoundException var7) {
            String strictEvnValue = System.getenv("STRICT");
            boolean isStrictMode = !StringUtils.isEmpty(strictEvnValue) && "KUAICTO".equalsIgnoreCase(strictEvnValue);
            if (isStrictMode) {
                throw new APIException(Enum401Error.USER_NOT_FOUND);
            }

            sessionUserFromGateway = defaultSessionUser;
        }

        sessionUserFromGateway.setOperationId(LoggerHelper.getReqNo());
        return sessionUserFromGateway;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public BasicController setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public BasicController setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public synchronized String getHttpBody() {
        if (StringUtils.isEmpty(this.httpBody)) {
            this.httpBody = HttpRender.readData(this.getRequest()).trim();
        }

        return this.httpBody;
    }

    public BasicController setHttpBody(String httpBody) {
        this.httpBody = httpBody;
        return this;
    }
}
