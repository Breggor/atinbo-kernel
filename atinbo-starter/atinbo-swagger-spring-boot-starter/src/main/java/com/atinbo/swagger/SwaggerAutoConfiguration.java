package com.atinbo.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zenghao
 * @date 2019-07-18
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "swagger", name = "enabled", havingValue = "true", matchIfMissing = false)
    public Docket autoEnableSwagger(SwaggerProperties swaggerProperties) {
        // api信息
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .build();

        // 忽略路径
        String excludePaths = swaggerProperties.getExcludePaths();
        List<Predicate<String>> exclude = new ArrayList<>();
        if (!StringUtils.isEmpty(excludePaths)) {
            exclude = Arrays.stream(excludePaths.split(","))
                    .map(PathSelectors::ant)
                    .collect(Collectors.toList());
        }

        // 构建
        return new Docket(DocumentationType.SWAGGER_2).groupName(swaggerProperties.getName())
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(Predicates.not(Predicates.or(exclude)))
                .build();

    }
}
