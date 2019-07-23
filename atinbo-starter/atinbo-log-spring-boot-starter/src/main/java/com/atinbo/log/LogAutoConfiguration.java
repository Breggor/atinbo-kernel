package com.atinbo.log;

import com.atinbo.log.aspect.SysLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zenghao
 * @date 2019/7/23
 * 日志自动配置
 */
@EnableAsync
@Configuration
@ConditionalOnProperty(prefix = "atinbo.log",name = "enabled", havingValue = "true")
public class LogAutoConfiguration {


    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}
