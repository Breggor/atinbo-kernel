package com.atinbo.webmvc.cors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    @ConditionalOnProperty(prefix = "cors", name = "enabled", havingValue = "true", matchIfMissing = false)
    public WebMvcConfigurer corsConfigurer(CorsProperties corsProperties) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(corsProperties.getPathPattern())
                        .allowedHeaders(corsProperties.getAllowedHeaders())
                        .allowedMethods(corsProperties.getAllowedMethods())
                        .allowedOrigins(corsProperties.getAllowedOrigins())
                        .allowCredentials(corsProperties.isAllowCredentials()).maxAge(corsProperties.getMaxAge());
                super.addCorsMappings(registry);
            }
        };
    }
}
