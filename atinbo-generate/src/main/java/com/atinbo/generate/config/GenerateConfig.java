package com.atinbo.generate.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 规则配置项
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenerateConfig {

    public static GenerateConfig defaultConfig(){
        return new GenerateConfig("atinbo generator", "com.atinbo", "mybatis", "dubbo", "", "src/java/main", false, null);
    }

    /**
     * 作者
     */
    private String author;

    /**
     * 项目包名
     */
    private String packageName;

    /**
     * 生成类型  hibernate 或者 mybatis
     */
    private String category;

    /**
     * 生成框架  dubbo 或者 springCloud
     */
    private String framework;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    private String tablePrefix;

    /**
     * 文件输出路径
     */
    private String outPath;

    /**
     * 是否开启多模块
     */
    private boolean multipleModule;

    /**
     * 模块名称
     */
    private String moduleName;

}
