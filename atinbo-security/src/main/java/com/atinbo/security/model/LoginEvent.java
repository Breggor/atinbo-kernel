package com.atinbo.security.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 用户登录日志
 *
 * @author breggor
 */
@Data
public class LoginEvent extends ApplicationEvent {

    public LoginEvent(LoginLog source) {
        super(source);
    }

    @Data
    @AllArgsConstructor
    public static class LoginLog {
        private String username;

        private String result;

        private String message;
    }
}
