package ${classInfo.packageName}.entity;

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass == "LocalDateTime" >
            <#assign importDdate = true />
        </#if>
        <#if fieldItem.fieldClass == "BigDecimal" >
            <#assign importBigDecimal = true />
        </#if>
    </#list>
</#if>
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
 *  ${classInfo.classComment}
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Accessors(chain = true)
@KeySequence("${classInfo.tableName?upper_case}_SEQ")
public class ${classInfo.className} implements Serializable {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
    /**
     * ${fieldItem.fieldComment}
     */
    <#if fieldItem.primaryKey>
    @TableId
    </#if>
    @ApiModelProperty("${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
    </#list>
</#if>
}