package com.atinbo.wx.mp;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * springboot配置
 *
 * @author breggor
 */
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
@Import({WxMpStorageAutoConfiguration.class, WxMpServiceAutoConfiguration.class})
public class WxMpAutoConfiguration {
}
