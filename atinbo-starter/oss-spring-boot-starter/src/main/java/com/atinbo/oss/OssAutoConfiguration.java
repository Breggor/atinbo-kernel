package com.atinbo.oss;

import com.atinbo.oss.config.OssProperties;
import com.atinbo.oss.processor.OssProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zenghao
 * @date 2019-11-13
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
@Import(value = { OssProcessor.class})
public class OssAutoConfiguration {

}
