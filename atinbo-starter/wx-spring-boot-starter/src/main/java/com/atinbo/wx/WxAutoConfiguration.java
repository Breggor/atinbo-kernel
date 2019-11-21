package com.atinbo.wx;

import com.atinbo.wx.config.RedisProperties;
import com.atinbo.wx.ma.WxMaProperties;
import com.atinbo.wx.ma.WxMaServiceAutoConfiguration;
import com.atinbo.wx.mp.WxMpProperties;
import com.atinbo.wx.mp.WxMpServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * springboot配置
 *
 * @author breggor
 */
@Configuration
@EnableConfigurationProperties({WxMpProperties.class, WxMaProperties.class, RedisProperties.class})
@Import({WxMpServiceAutoConfiguration.class, WxMaServiceAutoConfiguration.class})
public class WxAutoConfiguration {
}
