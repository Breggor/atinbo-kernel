package com.atinbo.webmvc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 跨域自动配置
 * cors.enabled=true
 * cors.pathPattern=/**
 * cors.allowedHeaders=Content-Type,requestId,platform,version,openId
 * cors.allowedMethods=GET, PUT, POST, DELETE, HEAD, OPTIONS
 * cors.allowedOrigins=http://dev-static.525happy.cn,http://pre-wx.525happy.cn,http://dev-drpadm.525happy.cn,http://dev-agtadm.525happy.cn
 * cors.allowCredentials=true
 * cors.maxAge=1800
 *
 * @author Breggor
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsAutoConfiguration {


    /**
     * response.addHeader("Access-Control-Allow-Origin", origin);
     * response.addHeader("Access-Control-Allow-Credentials", "true");
     * response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD, OPTIONS");
     * response.addHeader("Access-Control-Allow-Headers", "Content-Type,requestId,platform,version,openId");
     * response.addHeader("Access-Control-Max-Age", "1800");
     *
     * @param corsProperties
     */
    @Bean
    @ConditionalOnProperty(prefix = "cors", name = "enabled", havingValue = "true")
    public CorsConfigurationSource corsConfigurationSource(CorsProperties corsProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        Arrays.stream(corsProperties.getAllowedOrigins()).forEach(config::addAllowedOrigin);
        Arrays.stream(corsProperties.getAllowedHeaders()).forEach(config::addAllowedHeader);
        Arrays.stream(corsProperties.getAllowedMethods()).forEach(config::addAllowedMethod);
        config.setAllowCredentials(true);
        config.setMaxAge(corsProperties.getMaxAge());
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}