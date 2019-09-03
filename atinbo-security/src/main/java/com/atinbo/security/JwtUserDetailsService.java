package com.atinbo.security;


import com.atinbo.security.model.JwtUserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class JwtUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> user = findByUsername(username);
        if (user == null || user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create((String) user.getOrDefault("userId", "-1"),
                    (String) user.getOrDefault("username", "test"), (String) user.getOrDefault("password", "test"),
                    (List<String>) user.getOrDefault("roles", Arrays.asList("ROLE_USER")));
        }
    }

    protected abstract Map<String, Object> findByUsername(String username);
}