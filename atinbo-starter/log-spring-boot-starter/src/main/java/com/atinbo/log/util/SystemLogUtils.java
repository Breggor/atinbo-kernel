package com.atinbo.log.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.atinbo.log.model.SysLogSource;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 系统日志工具类
 *
 * @author zenghao
 */
@UtilityClass
public class SystemLogUtils {
    public SysLogSource getSystemLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLogSource logSource = new SysLogSource();
        logSource.setCreateBy(Objects.requireNonNull(getUsername()));
        //TODO 日志类型定义
        logSource.setType("0");
        logSource.setRemoteAddr(ServletUtil.getClientIP(request));
        logSource.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        logSource.setMethod(request.getMethod());
        logSource.setUserAgent(request.getHeader("user-agent"));
        logSource.setParams(HttpUtil.toParams(request.getParameterMap()));
        logSource.setServiceId(getClientId());
        return logSource;
    }

    /**
     * Gateway header X-GW-CLIENT-ID获取客户端
     *
     * @return clientId
     */
    private String getClientId() {
        return null;
    }

    /**
     * Gateway header X-GW-USER获取用户名称
     *
     * @return username
     */
    private String getUsername() {
        return "";
    }

}
