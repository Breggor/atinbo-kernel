package com.atinbo.wx.mp;

import com.atinbo.wx.config.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.atinbo.wx.mp.WxMpProperties.PREFIX;


/**
 * 微信接入相关配置属性.
 *
 * @author someone
 */
@Data
@ConfigurationProperties(PREFIX)
public class WxMpProperties {
    public static final String PREFIX = "wx.mp";

    /**
     * 设置微信公众号的appid.
     */
    private String appId;

    /**
     * 设置微信公众号的app secret.
     */
    private String secret;

    /**
     * 设置微信公众号的token.
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey.
     */
    private String aesKey;

    /**
     * 存储策略, memory, redis.
     */
    private StorageType type = StorageType.memory;


}
