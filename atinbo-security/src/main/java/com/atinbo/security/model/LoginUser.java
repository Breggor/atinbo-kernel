package com.atinbo.security.model;


import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser extends BaseUserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 当前登录Key
     */
    public static final String LOGIN_USER_KEY = "loginUserKey";

    /**
     * 测试用户
     */
    public static final LoginUser TEST = LoginUser.of(-1L, "test", "$2a$10$9jArgnaZMLNj.hm4GtnSv.iKMtr.rq3oYQB/izJo9TG2Z6Rq9g59S", Sets.newHashSet("ROLE_USER"));

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 扩展业务属性
     */
    private Map<String, Object> extra = new HashMap<>();

    /**
     * 用户唯一标识
     */
    private String token;

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

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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