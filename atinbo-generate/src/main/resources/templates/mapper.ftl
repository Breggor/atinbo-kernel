package ${classInfo.packageName}.mapper;

import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.model.${classInfo.className}Param;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
*  ${classInfo.classComment}
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Mapper
public interface ${classInfo.className}Mapper {

    ${classInfo.className}Mapper INSTANCE = Mappers.getMapper(${classInfo.className}Mapper.class);

    /**
     * param to entity
     */
    ${classInfo.className} to${classInfo.className}(${classInfo.className}Param ${classInfo.className?uncap_first}Param);

    /**
     * entity to BO
     */
    ${classInfo.className}BO to${classInfo.className?uncap_first}Bo(${classInfo.className} ${classInfo.className?uncap_first});

    /**
     * param to entity for update
     */
    ${classInfo.className} to${classInfo.className?uncap_first}(${classInfo.className}Param ${classInfo.className?uncap_first}Param, @MappingTarget ${classInfo.className} ${classInfo.className?uncap_first});

    /**
     * entityList to BOList
     */
    List<${classInfo.className}BO> to${classInfo.className}Bos(List<${classInfo.className}> ${classInfo.className?uncap_first}List);
}
