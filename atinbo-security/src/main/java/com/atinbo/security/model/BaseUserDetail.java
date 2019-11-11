package com.atinbo.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;


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
    private Collection<? extends GrantedAuthority> authorities;

    public BaseUserDetail(String username, String password, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
