package ${classInfo.packageName}.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import ${classInfo.packageName}.model.${classInfo.className};

/**
*  ${classInfo.classComment}
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Mapper
public interface ${classInfo.className}Mapper {

    /**
    * 新增
    */
    int insert(@Param("${classInfo.className?uncap_first}") ${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 删除
    */
    int delete(@Param("${classInfo.primaryField.fieldName}") ${classInfo.primaryField.fieldClass} id);

    /**
    * 更新
    */
    int update(@Param("${classInfo.className?uncap_first}") ${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * Load查询
    */
    ${classInfo.className} selectById(@Param("${classInfo.primaryField.fieldName}") ${classInfo.primaryField.fieldClass} id);

    /**
    * 分页查询Data
    */
	List<${classInfo.className}> pageList(@Param("offset") int offset, @Param("pagesize") int pagesize);

    /**
    * 分页查询Count
    */
    int pageListCount(@Param("offset") int offset, @Param("pagesize") int pagesize);

}
