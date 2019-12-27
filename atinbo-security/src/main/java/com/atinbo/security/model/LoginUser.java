package com.atinbo.security.model;


import com.atinbo.core.exception.BizException;
import com.atinbo.core.utils.CollectionUtil;
import com.atinbo.core.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 * @author breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser extends BaseUserDetail implements UserDetails {
    /**
     * 测试用户
     */
    public static final LoginUser TEST = LoginUser.of(-1L, "test", "$2a$10$9jArgnaZMLNj.hm4GtnSv.iKMtr.rq3oYQB/izJo9TG2Z6Rq9g59S", Sets.newHashSet("ROLE_USER"));
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 扩展业务属性
     */
    private Map<String, Object> extra = new HashMap<>();

    /**
     * 用户访问唯一标识
     */
    private String accessToken;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;


    public LoginUser(Long userId, String username, String password, Set<String> authorities) {
        this(userId, username, password, authorities, new HashMap<>(1));
    }

    public LoginUser(Long userId, String username, String password, Set<String> authorities, Map<String, Object> extra) {
        super(username, password, mapToGrantedAuthorities(authorities));
        this.userId = userId;
        this.extra = extra;
    }


    /**
     * 创建用户
     *
     * @param userId
     * @param username
     * @return
     */
    public static LoginUser of(Long userId, String username) {
        return new LoginUser(userId, username, "wx_openid", Sets.newHashSet("ROLE_USER"));
    }

    /**
     * 创建用户
     *
     * @param userId
     * @param username
     * @param password
     * @param authorities
     * @return
     */
    public static LoginUser of(Long userId, String username, String password, Set<String> authorities) {
        return new LoginUser(userId, username, password, authorities);
    }


    /**
     * 创建用户
     *
     * @param userId
     * @param username
     * @param password
     * @param authorities
     * @param extra
     * @return
     */
    public static LoginUser of(Long userId, String username, String password, Set<String> authorities, Map<String, Object> extra) {
        return new LoginUser(userId, username, password, authorities, extra);
    }

    private static Set<BaseGrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream().map(BaseGrantedAuthority::new).collect(Collectors.toSet());
    }


    /**
     * 扩展map中直接获取对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObjectFromExtra(String key, Class<T> clazz) {
        Object value = getExtra().get(key);
        if (value == null) {
            throw new BizException(String.format("key=%s 没有对应的值", key));
        }
        return (T) JsonUtil.parse((String) value, clazz);
    }

    @JsonIgnore
    public Set<String> getPermissions() {
        if (CollectionUtil.isEmpty(this.getAuthorities())) {
            return Collections.EMPTY_SET;
        }
        return this.getAuthorities().stream().map(BaseGrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}