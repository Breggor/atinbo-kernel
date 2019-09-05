package com.atinbo.generate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 规则配置项
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "atinbo.generate")
public class GenerateProperties {

    public static final String GENERATE_PREFIX = "atinbo.generate";

    /**
     * 作者
     */
    private String author = "atinbo generator";

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 模块配置
     */
    private Module module;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    private String tablePrefix;

    /**
     * 文件输出路径
     */
    private String outPath;

    @Data
    public static class Module {

        /**
         * 是否开启多模块
         */
        private boolean multiple;

        /**
         * 模块名称
         */
        private String name;
    }
}
