package ${classInfo.packageName}.openapi.model;

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass == "Date">
            <#assign importDdate = true />
        </#if>
        <#if fieldItem.fieldClass == "BigDecimal">
            <#assign importBigDecimal = true />
        </#if>
    </#list>
</#if>
import lombok.Data;
import lombok.experimental.Accessors;

import io.swagger.annotations.ApiModelProperty;

<#if importDdate??>
import java.util.Date;
</#if>
<#if importBigDecimal??>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
 *  ${classInfo.classComment} 入参参数
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Accessors(chain = true)
public class ${classInfo.className}Form implements Serializable {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >

    @ApiModelProperty(value = "${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

</#list>
</#if>
}