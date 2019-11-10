package com.atinbo.security.model;


import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 */
@Setter
public class LoginUser extends User {
    /**
     * 测试用户
     */
    public static final LoginUser TEST = LoginUser.of(-1L, "test" , "$2a$10$9jArgnaZMLNj.hm4GtnSv.iKMtr.rq3oYQB/izJo9TG2Z6Rq9g59S" , Sets.newHashSet("ROLE_USER"));
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @Getter
    private final Long userId;

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
     * 权限列表
     */
    @Getter
    private Set<String> permissions;

    public LoginUser(Long userId, String username, String password, Set<String> authorities) {
        super(username, password, mapToGrantedAuthorities(authorities));
        this.userId = userId;
        this.permissions = authorities;
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

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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