package com.atinbo.mvc.resolver;

/**
 * 没有请求Id异常
 *
 * @author breggor
 */
public class RequestIdNotFoundException extends Exception {
    public RequestIdNotFoundException(String msg) {
        super(msg);
    }
}
