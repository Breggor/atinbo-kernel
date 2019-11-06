package ${classInfo.packageName}.impl;

import com.atinbo.model.Outcome;
import com.atinbo.model.Pagination;
import com.atinbo.model.PageParam;
import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.model.${classInfo.className}Param;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.mapper.${classInfo.className}Mapper;
import ${classInfo.packageName}.dao.${classInfo.className}Dao;
import ${classInfo.packageName}.service.${classInfo.className}Service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
*  ${classInfo.classComment}
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Slf4j
@Service
public class ${classInfo.className}ServiceImpl implements ${classInfo.className}Service {

    @Autowired
    private ${classInfo.className}Dao ${classInfo.className?uncap_first}Dao;

    /**
     * 新增
     */
    @Override
    public Outcome<${classInfo.className}BO> save(${classInfo.className}Param ${classInfo.className?uncap_first}Param) {
        ${classInfo.className} ${classInfo.className?uncap_first} = ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}(${classInfo.className?uncap_first}Param);
        ${classInfo.className?uncap_first}Dao.insert(${classInfo.className?uncap_first});
        return Outcome.ofSuccess(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bo(${classInfo.className?uncap_first}));
    }

    /**
     * 删除
     */
    @Override
    public boolean deleteById(${classInfo.primaryField.fieldClass} id) {
        if (id == null) {
            return false;
        }
        return ${classInfo.className?uncap_first}Dao.deleteById(id) > 0;
    }

    /**
     * 更新
     */
    @Override
    public boolean update(${classInfo.className}Param ${classInfo.className?uncap_first}Param) {
        if (${classInfo.className?uncap_first}Param.get${classInfo.primaryField.fieldName?cap_first}() == null) {
            return false;
        }
        ${classInfo.className} entity = ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}(${classInfo.className?uncap_first}Param);
        return ${classInfo.className?uncap_first}Dao.updateById(entity) > 0;
    }

    /**
     * 根据主键查询
     */
    @Override
    public Outcome<${classInfo.className}BO> findById(${classInfo.primaryField.fieldClass} id) {
        ${classInfo.className} ${classInfo.className?uncap_first} = ${classInfo.className?uncap_first}Dao.selectById(id);
        return Outcome.ofSuccess(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bo(${classInfo.className?uncap_first}));
    }

    /**
     * 分页查询
     */
    @Override
    public Outcome<List<${classInfo.className}BO> pageList(${classInfo.className}Param ${classInfo.className?uncap_first}Param, PageParam pageParam){
        PageRequest pageRequest = PageRequest.of(${classInfo.className?uncap_first}Param.getPage(), ${classInfo.className?uncap_first}Param.getSize());
        Page<${classInfo.className}> page = ${classInfo.className?uncap_first}Dao.findAll(DynamicSpecifications.toSpecification(${classInfo.className?uncap_first}Param), pageRequest);

        List<${classInfo.className}BO> ${classInfo.className?uncap_first}Bos = ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bos(page.getContent());
        return Outcome.ofSuccess(Pagination.of(page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements()), ${classInfo.className?uncap_first}Bos);
    }
}