package com.atinbo.wx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

import static com.atinbo.wx.config.RedisProperties.PREFIX;

/**
 * Redis配置.
 *
 * @author breggor
 */
@Data
@ConfigurationProperties(PREFIX)
public class RedisProperties implements Serializable {
    public static final String PREFIX = "wx.redis" ;

    private static final long serialVersionUID = -5924815351660074401L;

    /**
     * 主机地址.
     */
    private String host = "127.0.0.1" ;

    /**
     * 端口号.
     */
    private int port = 6379;

    /**
     * 密码.
     */
    private String password;

    /**
     * 超时.
     */
    private int timeout = 2000;

    /**
     * 数据库.
     */
    private int database = 0;

    private Integer maxActive;
    private Integer maxIdle;
    private Integer maxWaitMillis;
    private Integer minIdle;
}
