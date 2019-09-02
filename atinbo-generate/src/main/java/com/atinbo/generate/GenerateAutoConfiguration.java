package com.atinbo.generate;

import com.atinbo.generate.config.GenerateProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author zenghao
 * @date 2019-09-02
 */
@MapperScan("com.atinbo.generate.mapper")
@EnableConfigurationProperties(GenerateProperties.class)
@ConditionalOnProperty(prefix = GenerateProperties.GENERATE_PREFIX , name = "enabled", havingValue = "true", matchIfMissing = false)
public class GenerateAutoConfiguration {

}
