package com.atinbo.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


/**
 * spring security基础属性类
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 账户是否未过期
     */
    private boolean accountNonExpired = true;

    /**
     * 账户是否未锁定
     */
    private boolean accountNonLocked = true;

    /**
     * 密码是否未过期
     */
    private boolean credentialsNonExpired = true;

    /**
     * 账户是否激活
     */
    private boolean enabled = true;

    /**
     * 角色 ROLE_USER
     */
    private Set<BaseGrantedAuthority> authorities;

    public BaseUserDetail(String username, String password, Set<BaseGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
