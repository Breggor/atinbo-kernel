package com.atinbo.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Map;

/**
 * 网关登录用户
 *
 * @author breggor
 */
@Data
@Accessors(chain = true)
public class GatewayUser {
    public static final GatewayUser ANONYMOUS = new GatewayUser().setUsername("anonymous").setUserId(-1L);

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 用户头像URL
     */
    private String avatar;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 最新登录时间
     */
    private Date lastLoginTime;

    /**
     * 扩展属性
     */
    private Map<String, Object> extras;
}
