package com.atinbo.webmvc.exceptions;


import com.atinbo.core.exception.RequestParamException;

/**
 * 没有找到用户异常
 *
 * @author breggor
 */
public class UserNotFoundException extends RequestParamException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
