package com.atinbo.webmvc.exception;


/**
 * 没有找到用户异常
 *
 * @author breggor
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
