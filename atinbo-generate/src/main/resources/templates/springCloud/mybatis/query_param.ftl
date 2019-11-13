package ${classInfo.packageName}.model;

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass == "LocalDateTime">
            <#assign importDdate = true />
        </#if>
        <#if fieldItem.fieldClass == "BigDecimal">
            <#assign importBigDecimal = true />
        </#if>
    </#list>
</#if>
import com.atinbo.model.PageParam;
import com.atinbo.model.QueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

<#if importDdate??>
import java.time.LocalDateTime;
</#if>
<#if importBigDecimal??>
import java.math.BigDecimal;
</#if>

/**
 *  ${classInfo.classComment}
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Accessors(chain = true)
public class ${classInfo.className}QueryParam extends PageParam implements QueryParam {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
    /**
     * ${fieldItem.fieldComment}
     */
    @ApiModelProperty("${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
    </#list>
</#if>
}