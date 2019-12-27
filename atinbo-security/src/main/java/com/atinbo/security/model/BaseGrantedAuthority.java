package com.atinbo.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;


/**
 * 授权
 *
 * @author breggor
 */
@Data
@NoArgsConstructor
public final class BaseGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 8042674896077075133L;
    private String authority;

    public BaseGrantedAuthority(String authority) {
        Objects.requireNonNull(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }
}
