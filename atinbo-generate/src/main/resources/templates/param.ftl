package ${classInfo.packageName}.model;

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

import com.atinbo.model.PageParam;
import com.atinbo.model.QueryParam;
<#if importDdate??>
import java.util.Date;
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
public class ${classInfo.className}Param extends PageParam implements QueryParam {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
    /**
    * ${fieldItem.fieldComment}
    */
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

</#list>
</#if>
}