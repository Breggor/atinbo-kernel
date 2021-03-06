package ${classInfo.packageName}.openapi.mapper;

import com.atinbo.model.Pageable;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.model.${classInfo.className}Param;
import ${classInfo.packageName}.model.${classInfo.className}QueryParam;
import ${classInfo.packageName}.openapi.model.${classInfo.className}VO;
import ${classInfo.packageName}.openapi.model.${classInfo.className}Form;
import ${classInfo.packageName}.openapi.model.${classInfo.className}QueryForm;
import org.mapstruct.Mapper;
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
     * form to param
     */
    ${classInfo.className}Param to${classInfo.className}Param(${classInfo.className}Form ${classInfo.className?uncap_first}Form);

    /**
     * form to param
     */
    ${classInfo.className}QueryParam to${classInfo.className}Param(${classInfo.className}QueryForm ${classInfo.className?uncap_first}Form);

    /**
     * BO to VO
     */
    ${classInfo.className}VO to${classInfo.className}Vo(${classInfo.className}BO ${classInfo.className?uncap_first});

    /**
     * BOList to VOList
     */
    List<${classInfo.className}VO> to${classInfo.className}Vos(List<${classInfo.className}BO> ${classInfo.className?uncap_first}List);

    /**
     * BOPage to VOPage
     */
    Pageable<${classInfo.className}VO> to${classInfo.className}Vos(Pageable<${classInfo.className}BO> ${classInfo.className?uncap_first}List);
}
