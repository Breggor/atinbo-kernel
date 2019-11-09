# atinbo-generator

#### 介绍
代码生成

本项目基于 atinbo-model、atinbo-core 生成代码

#### 安装教程
在 service 项目中加上
<pre>
    <code>
        &lt;dependency&gt;
            &lt;groupId&gt;com.atinbo&lt;/groupId&gt;
            &lt;artifactId&gt;atinbo-generate&lt;/artifactId&gt;
            &lt;version&gt;${version}&lt;/version&gt;
        &lt;/dependency&gt;
    </code>
</pre>

#### 使用说明

在项目中配置文件（bootstrap.yml或者applicaiton.yml）中增加相关配置
然后启动服务
打开 http://ip:post/gen.html 生成代码

配置示例：
<pre>
    <code>
atinbo:
  generate:
    author: zenghao
    category: mybatis
    table-prefix: ls_
    package-name: com.atinbo.saas
    out-path: src/main/java
    module:
      multiple: true
      name: saas-plat
    </code>
</pre>

配置说明

|KEY|说明|类型|默认值|描述
|:------------- |:-------:|:------|-----:|:------------|
|atinbo.generate.author|作者|string|atinbo generator|生成类文件作者注释
|atinbo.generate.category|类型|enum|mybatis|mybatis/hibernate
|atinbo.generate.table-prefix|表名前缀|string| |生成类文件名将去掉该前缀
|atinbo.generate.package-name|包名|string| |生成类文件包名前缀
|atinbo.generate.out-path|输出路径|string|src/java/main|生成文件地址。当前项目相对地址
|atinbo.generate.module.multiple|是否开启多模块|boolean| |开启后将生成的代码按 openapi、api、service 模块分开并放入相应的目录下
|atinbo.generate.module.name|模块名称|string| |代码生成在该模块目录下。如果同时开启多模块，文件将分开放入 模块名+openapi、api、service 目录下

