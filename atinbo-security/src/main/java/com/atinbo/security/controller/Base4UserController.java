package com.atinbo.security.controller;

import com.atinbo.security.model.LoginUser;
import com.atinbo.security.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 能获取当前用户控制类
 *
 * @author breggor
 */
@Slf4j
public class Base4UserController {


    @Autowired
    protected HttpServletRequest request;

    @Autowired
    UserTokenService userTokenService;

    protected LoginUser getCurrentUser() {
        LoginUser loginUser = userTokenService.getLoginUser(request);
        Objects.requireNonNull(loginUser, "当前没有登录用户");
        return loginUser;
    }
}
