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
     * 项目包名
     */
    private String packageName = "com.atinbo";

    /**
     * 生成类型  hibernate 或者 mybatis
     */
    private String category = "mybatis";

    /**
     * 表前缀(类名不会包含表前缀)
     */
    private String tablePrefix = "";

    /**
     * 文件输出路径
     */
    private String outPath = "src/java/main";

    /**
     * 模块配置
     */
    private Module module;

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
