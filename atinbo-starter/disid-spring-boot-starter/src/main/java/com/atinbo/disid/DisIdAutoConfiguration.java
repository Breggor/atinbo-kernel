package com.atinbo.disid;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;


/**
 * 分布式ID配置类
 *
 * @author breggor
 */
@Configuration
@ConditionalOnProperty(prefix = DisIdAutoConfiguration.SNOWFLAKE_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = false)
public class DisIdAutoConfiguration {
    public final static String SNOWFLAKE_PREFIX = "snowflake";

    @Value("${snowflake.workerId}")
    private long workerId;


    @Bean
    @ConditionalOnMissingBean
    SnowflakeService snowFlakeService() {
        Objects.requireNonNull(workerId, "[workerId] 不能为null");
        return new SnowflakeService(workerId);
    }
}
