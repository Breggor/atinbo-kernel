package ${classInfo.packageName}.model;

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass == "LocalDateTime">
            <#assign importDateTime = true />
        </#if>
        <#if fieldItem.fieldClass == "LocalDate">
            <#assign importDate = true />
        </#if>
        <#if fieldItem.fieldClass == "LocalTime">
            <#assign importTime = true />
        </#if>
        <#if fieldItem.fieldClass == "BigDecimal">
            <#assign importBigDecimal = true />
        </#if>
    </#list>
</#if>
import lombok.Data;
import lombok.experimental.Accessors;

import com.atinbo.model.QueryParam;
<#if importDateTime??>
import java.time.LocalDateTime;
</#if>
<#if importDate??>
import java.time.LocalDate;
</#if>
<#if importTime??>
import java.time.LocalTime;
</#if>
<#if importBigDecimal??>
import java.math.BigDecimal;
</#if>

/**
 *  ${classInfo.classComment} PARAM
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Accessors(chain = true)
public class ${classInfo.className}Param implements QueryParam {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
    /**
    * ${fieldItem.fieldComment}
    */
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

</#list>
</#if>
}