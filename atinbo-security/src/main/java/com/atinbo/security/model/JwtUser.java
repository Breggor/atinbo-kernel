
package com.atinbo.security.model;


import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Setter
public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(
            String id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public String getId() {
        return id;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 账户是否激活
    @Override
    public boolean isEnabled() {
        return true;
    }
}