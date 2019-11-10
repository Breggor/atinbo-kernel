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
import lombok.experimental.Accessors;

<#if importDdate??>
    import java.util.Date;
</#if>
<#if importBigDecimal??>
    import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
*  ${classInfo.classComment} BO
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Data
@Accessors(chain = true)
public class ${classInfo.className}BO implements Serializable {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        /**
        * ${fieldItem.fieldComment}
        */
        private ${fieldItem.fieldClass} ${fieldItem.fieldName};

    </#list>
</#if>
}