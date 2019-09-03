package ${classInfo.packageName}.service;

import java.util.Map;
import com.atinbo.model.Outcome;
import com.atinbo.model.PageOutcome;

import ${classInfo.packageName}.model.${classInfo.className}BO;

/**
*  ${classInfo.classComment}
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
public interface ${classInfo.className}Service {

    /**
    * 新增
    */
    boolean insert(${classInfo.className}BO ${classInfo.className?uncap_first});

    /**
    * 删除
    */
    boolean delete(${classInfo.primaryField.fieldClass} id);

    /**
    * 更新
    */
    boolean update(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 根据主键查询
    */
    ${classInfo.className}BO findById(${classInfo.primaryField.fieldClass} id);

    /**
    * 分页查询
    */
    Map<String,Object> pageList(int offset, int pagesize);

}
