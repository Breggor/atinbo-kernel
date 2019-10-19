package ${classInfo.packageName}.entity;

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

import java.io.Serializable;
<#if importDdate??>
import java.util.Date;
</#if>
<#if importBigDecimal??>
import java.math.BigDecimal;
</#if>

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableId;

/**
*  ${classInfo.classComment}
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Data
@TableName("${classInfo.tableName}")
public class ${classInfo.className} extends Model<${classInfo.className}> implements Serializable {

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