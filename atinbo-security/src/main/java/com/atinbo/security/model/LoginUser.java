package com.atinbo.security.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 */
@Setter
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 测试用户
     */
    public static final LoginUser TEST = new LoginUser("-1", "test", "$2a$10$9jArgnaZMLNj.hm4GtnSv.iKMtr.rq3oYQB/izJo9TG2Z6Rq9g59S", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

    /**
     * 用户ID
     */
    @Getter
    private final String userId;

    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * 角色 ROLE_USER
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * 扩展业务属性
     */
    @Getter
    private Map<String, Object> extra = new HashMap<>();

    /**
     * 用户唯一标识
     */
    @Getter
    private String token;

    /**
     * 登陆时间
     */
    @Getter
    private Long loginTime;

    /**
     * 过期时间
     */
    @Getter
    private Long expireTime;

    /**
     * 登录IP地址
     */
    @Getter
    private String ip;

    /**
     * 登录地点
     */
    @Getter
    private String location;

    /**
     * 浏览器类型
     */
    @Getter
    private String browser;

    /**
     * 操作系统
     */
    @Getter
    private String os;

    /**
     * 创建用户
     *
     * @param userId
     * @param username
     * @param password
     * @param roles
     * @return
     */
    public static LoginUser of(String userId, String username, String password, List<String> roles) {
        return new LoginUser(userId, username, password, mapToGrantedAuthorities(roles));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public LoginUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * 权限列表
     */
    public Set<String> getPermissions() {
        if (CollectionUtils.isEmpty(authorities)) {
            return Collections.EMPTY_SET;
        }
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    /**
     * 返回分配给用户的角色列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否激活
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    /**
     * 扩展map中直接获取对象
     *
     * @param key
     * @param defVal 默认值
     * @param <T>
     * @return
     */
    public <T> T getObjectFromExtra(String key, T defVal) {
        Object value = getExtra().get(key);
        if (value == null && defVal != null) {
            return defVal;
        }
        return (T) value;
    }
}