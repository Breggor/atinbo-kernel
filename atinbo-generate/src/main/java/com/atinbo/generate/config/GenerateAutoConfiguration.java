package com.atinbo.generate.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author zenghao
 * @date 2019-09-02
 */
@MapperScan("com.atinbo.generate.mapper")
@EnableConfigurationProperties(GenerateProperties.class)
public class GenerateAutoConfiguration {
}
