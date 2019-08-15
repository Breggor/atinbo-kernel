package com.atinbo.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger API文档配置
 *
 * @author zenghao
 * @date 2019-07-18
 */
@Data
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
public class SwaggerProperties {

    public static final String SWAGGER_PREFIX = "swagger";

    /**
     * 名称
     */
    private String name;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本号
     */
    private String version;
    /**
     * 许可证
     */
    private String license;
    /**
     * 许可证URL
     */
    private String licenseUrl;
    /**
     * 排除路径
     */
    private String excludePaths;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系URL
     */
    private String contactUrl;
    /**
     * 联系邮箱
     */
    private String contactEmail;
}