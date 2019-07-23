package com.atinbo.log;

import com.atinbo.log.aspect.SysLogAspect;
import com.atinbo.log.event.SysLogListener;
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
public class LogAutoConfiguration {

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener();
    }
}
