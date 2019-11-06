package com.atinbo.security.handler;

import com.alibaba.fastjson.JSON;
import com.atinbo.core.ServletUtils;
import com.atinbo.core.http.model.ResultVO;
import com.atinbo.security.model.LoginUser;
import com.atinbo.security.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义退出处理类 返回成功
 *
 * @author breggor
 */
@Slf4j
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private UserTokenService userTokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = userTokenService.getLoginUser(request);
        if (!Objects.isNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 记录用户退出日志
            log.info(userName + "-- 退出成功");
        }
        ServletUtils.renderString(response, JSON.toJSONString(ResultVO.success().setMessage("退出成功")));
    }
}
