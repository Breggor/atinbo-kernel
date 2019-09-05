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
<pre>
    <code>
atinbo:
  generate:
    enabled: true
    author: zenghao
    table-prefix: ls_
    package-name: com.atinbo.saas
    out-path: src/main/java
    module:
      multiple: true
      name: saas-plat
    </code>
</pre>

配置说明

KEY|说明|必填|默认值
atinbo.generate.enabled|是否启用|true|false
atinbo.generate.author|生成文件作者|false|atinbo generator
atinbo.generate.table-prefix|表名前缀|false|

然后启动服务启动
打开 http://ip:post/gen.html 生成代码
