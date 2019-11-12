package com.atinbo.security.utils;


import com.atinbo.security.exception.UnAuthorizedException;
import com.atinbo.security.model.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 *
 * @author breggor
 */


public class SecurityUtils {
    /**
     * 获取用户ID
     **/
    public static Long getUserId() {
        try {
            return ((LoginUser) getUserDetails()).getUserId();
        } catch (Exception e) {
            throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "获取用户账户异常");
        }
    }

    /**
     * 获取用户名
     **/
    public static String getUsername() {
        try {
            return getUserDetails().getUsername();
        } catch (Exception e) {
            throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "获取用户账户异常");
        }
    }

    /**
     * 获取用户
     **/
    public static UserDetails getUserDetails() {
        try {
            return (UserDetails) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
