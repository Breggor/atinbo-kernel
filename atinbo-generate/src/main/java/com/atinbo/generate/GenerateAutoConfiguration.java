package com.atinbo.generate;

import com.atinbo.generate.config.GenerateProperties;
import freemarker.template.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zenghao
 * @date 2019-09-02
 */
@MapperScan("com.atinbo.generate.mapper")
@EnableConfigurationProperties(GenerateProperties.class)
@ComponentScan(basePackages = "com.atinbo.generate")
public class GenerateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        return configuration;
    }
}
