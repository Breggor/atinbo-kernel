package ${classInfo.packageName}.entity;

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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;

/**
 *  ${classInfo.classComment}
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "${classInfo.tableName}")
public class ${classInfo.className} implements Serializable {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.primaryKey>
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_seq")
    @TableGenerator(name = "id_seq", table = "id_seq", allocationSize = 1,
        pkColumnName = "seq_pk", pkColumnValue = "${classInfo.tableName}")
        <#else >
    /**
     * ${fieldItem.fieldComment}
     */
       </#if>
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

    </#list>
</#if>
}