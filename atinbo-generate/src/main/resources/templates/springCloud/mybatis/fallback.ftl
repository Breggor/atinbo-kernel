package ${classInfo.packageName}.feign.fallback;

import ${classInfo.packageName}.feign.I${classInfo.className}Client;
import ${classInfo.packageName}.model.*;

/**
 *  ${classInfo.classComment} fallback
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
public class ${classInfo.className}ClientFallback implements I${classInfo.className}Client {

    @Override
    Outcome create(${classInfo.className}Param param) {
        return Outcome.failure("接口异常");
    }

    @Override
    Outcome<Pagable<${classInfo.className}DTO>> findAllByPage(${classInfo.className}QueryParam param) {
        return Outcome.failure("接口异常");
    }

    @Override
    Outcome<${classInfo.className}DTO> findById(${classInfo.primaryField.fieldClass} id){
        return Outcome.failure("接口异常");
        }

    @Override
    Outcome<${classInfo.className}DTO> modify(${classInfo.primaryField.fieldClass} id, ${classInfo.className}Param param){
        return Outcome.failure("接口异常");
    }

    @Override
    boolean deleteById(${classInfo.primaryField.fieldClass} id) {
        return false;
    }

}