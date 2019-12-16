package com.atinbo.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zenghao
 * @date 2019-07-18
 */
@Configuration
@EnableKnife4j
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnBean(annotation = EnableSwagger2.class)
@Import(SwaggerModelReader.class)
public class SwaggerAutoConfiguration {
    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(Predicates.not(Predicates.or(buildExcludePredicates())))
                .build().securityContexts(buildSecurityContexts()).securitySchemes(buildApiKeys());
    }

    // 忽略路径
    private List<Predicate<String>> buildExcludePredicates() {
        List<Predicate<String>> exclude = new ArrayList<>();
        if (!CollectionUtils.isEmpty(swaggerProperties.getExcludePaths())) {
            exclude = swaggerProperties.getExcludePaths().stream().map(PathSelectors::ant)
                    .collect(Collectors.toList());
        }
        return exclude;
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .build();
    }

    private List<ApiKey> buildApiKeys() {
        List<ApiKey> result = Lists.newArrayList();
        result.add(new ApiKey("Authorization", "Authorization", "header"));
        if (null != swaggerProperties.getApiKeys() && !swaggerProperties.getApiKeys().isEmpty()) {
            swaggerProperties.getApiKeys().forEach(o -> {
                result.add(new ApiKey(o.getName(), o.getKeyname(), o.getPassAs()));
            });
        }
        return result;
    }

    private List<SecurityContext> buildSecurityContexts() {
        List<SecurityContext> result = Lists.newArrayList();
        SecurityContext sc = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();

        return result;
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = Lists.newArrayList();
        AuthorizationScope[] authScopes = new AuthorizationScope[]{new AuthorizationScope("global", "全局设置")};

        buildApiKeys().forEach(o -> {
            result.add(new SecurityReference(o.getName(), authScopes));
        });
        return result;
    }
}