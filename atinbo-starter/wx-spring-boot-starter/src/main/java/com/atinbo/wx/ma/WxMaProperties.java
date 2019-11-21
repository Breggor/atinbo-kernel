package com.atinbo.wx.ma;

import com.atinbo.wx.config.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zenghao
 * @date 2019-11-21
 */
@Data
@ConfigurationProperties(WxMaProperties.PREFIX)
public class WxMaProperties {

    public static final String PREFIX = "wx.ma";

    /**
     * 设置微信小程序的appid.
     */
    private String appId;

    /**
     * 设置微信小程序的app secret.
     */
    private String secret;

    /**
     * 设置微信小程序的token.
     */
    private String token;

    /**
     * 设置微信小程序的EncodingAESKey.
     */
    private String aesKey;

    /**
     * 存储策略, memory, redis.
     */
    private StorageType type = StorageType.memory;
}
