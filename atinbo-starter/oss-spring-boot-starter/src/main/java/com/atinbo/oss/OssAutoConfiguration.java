package com.atinbo.oss;

import com.aliyun.oss.OSS;
import com.atinbo.oss.config.OssProperties;
import com.atinbo.oss.processor.OssService;
import com.atinbo.oss.strategy.DefaultStrategy;
import com.atinbo.oss.strategy.RenameStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zenghao
 * @date 2019-11-13
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RenameStrategy defaultStrategy() {
        return new DefaultStrategy();
    }


    @Bean
    @ConditionalOnMissingBean
    public OssService ossService(OssProperties properties, OSS client, RenameStrategy renameStrategy) {
        return new OssService(properties, client, renameStrategy);
    }
}