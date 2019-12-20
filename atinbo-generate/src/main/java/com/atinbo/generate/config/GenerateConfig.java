package com.atinbo.generate.config;

import com.atinbo.core.utils.StringUtil;
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

    public static final String DEFAULT_OUT_PATH = "src/main/java";
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
     * 模块名称
     */
    private String moduleName;

    public static GenerateConfig defaultConfig() {
        return new GenerateConfig("atinbo generator", "com.atinbo", "mybatis", "dubbo", "", DEFAULT_OUT_PATH, null);
    }

    public GenerateConfig setAuthor(String author) {
        if (StringUtil.isNotBlank(author)) {
            this.author = author;
        }
        return this;
    }

    public GenerateConfig setPackageName(String packageName) {
        if (StringUtil.isNotBlank(packageName)) {
            this.packageName = packageName;
        }
        return this;
    }

    public GenerateConfig setCategory(String category) {
        if (StringUtil.isNotBlank(category)) {
            this.category = category;
        }
        return this;
    }

    public GenerateConfig setFramework(String framework) {
        if (StringUtil.isNotBlank(framework)) {
            this.framework = framework;
        }
        return this;
    }

    public GenerateConfig setTablePrefix(String tablePrefix) {
        if (StringUtil.isNotBlank(tablePrefix)) {
            this.tablePrefix = tablePrefix;
        }
        return this;
    }

    public GenerateConfig setOutPath(String outPath) {
        if (StringUtil.isNotBlank(outPath)) {
            this.outPath = outPath;
        }
        return this;
    }

    public GenerateConfig setModuleName(String moduleName) {
        if (StringUtil.isNotBlank(moduleName)) {
            this.moduleName = moduleName;
        }
        return this;
    }
}
