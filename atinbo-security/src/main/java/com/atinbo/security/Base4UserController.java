package com.atinbo.security;

import com.atinbo.security.model.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    protected HttpServletResponse response;


    protected JwtUser getCurrentUser()  {
        JwtUser user = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Objects.requireNonNull(user, "当前没有登录用户");
        return user;
    }
}
