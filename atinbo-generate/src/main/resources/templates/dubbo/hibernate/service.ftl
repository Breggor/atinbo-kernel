package ${classInfo.packageName}.service;

import com.atinbo.model.Outcome;
import com.atinbo.model.PageParam;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.model.${classInfo.className}Param;
import ${classInfo.packageName}.model.${classInfo.className}QueryParam;

import java.util.List;

/**
 *  ${classInfo.classComment} Service
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
public interface ${classInfo.className}Service {

    /**
     * 新增
     */
    Outcome<${classInfo.className}BO> save(${classInfo.className}Param ${classInfo.className?uncap_first}Param);

    /**
     * 删除
     */
    boolean deleteById(${classInfo.primaryField.fieldClass} id);

    /**
     * 更新
     */
    boolean update(${classInfo.className}Param ${classInfo.className?uncap_first}Param);

    /**
     * 根据主键查询
     */
    Outcome<${classInfo.className}BO> findById(${classInfo.primaryField.fieldClass} id);

    /**
     * 分页查询
     */
    Outcome<List<${classInfo.className}BO>> pageList(${classInfo.className}QueryParam param, PageParam pageParam);

}
