package ${classInfo.packageName}.impl;

import com.atinbo.jpa.DynamicSpecifications;
import com.atinbo.model.Outcome;
import com.atinbo.model.Pagination;
import com.atinbo.model.PageParam;
import com.atinbo.utils.PageUtil;
import com.atinbo.core.exception.RequestParamException;
import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.model.${classInfo.className}Param;
import ${classInfo.packageName}.model.${classInfo.className}QueryParam;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.mapper.${classInfo.className}Mapper;
import ${classInfo.packageName}.repository.${classInfo.className}Repository;
import ${classInfo.packageName}.service.${classInfo.className}Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

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
    private ${classInfo.className}Repository ${classInfo.className?uncap_first}Repository;

    /**
     * 新增
     */
    @Override
    public Outcome<${classInfo.className}BO> save(${classInfo.className}Param ${classInfo.className?uncap_first}Param) {
        ${classInfo.className} ${classInfo.className?uncap_first} = ${classInfo.className?uncap_first}Repository.saveAndFlush(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}(${classInfo.className?uncap_first}Param));
        return Outcome.success(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bo(${classInfo.className?uncap_first}));
    }

    /**
     * 删除
     */
    @Override
    public boolean deleteById(${classInfo.primaryField.fieldClass} id) {
        if (id == null) {
            return false;
        }
        ${classInfo.className?uncap_first}Repository.deleteById(id);
        return true;
    }

    /**
     * 更新
     */
    @Override
    public boolean update(${classInfo.className}Param ${classInfo.className?uncap_first}Param) {
        if (${classInfo.className?uncap_first}Param.get${classInfo.primaryField.fieldName?cap_first}() == null) {
            return false;
        }
        ${classInfo.className} entity = ${classInfo.className?uncap_first}Repository.getOne(${classInfo.className?uncap_first}Param.get${classInfo.primaryField.fieldName?cap_first}());
        ${classInfo.className} ${classInfo.className?uncap_first} = ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}(${classInfo.className?uncap_first}Param, entity);
        ${classInfo.className?uncap_first}Repository.saveAndFlush(${classInfo.className?uncap_first});
        return true;
    }

    /**
     * 根据主键查询
     */
    @Override
    public Outcome<${classInfo.className}BO> findById(${classInfo.primaryField.fieldClass} id) {
        Optional<${classInfo.className}> opt = ${classInfo.className?uncap_first}Repository.findById(id);
        ${classInfo.className} ${classInfo.className?uncap_first} = opt.orElseThrow(() -> new RequestParamException("没有找到信息"));;
        return Outcome.success(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bo(${classInfo.className?uncap_first}));
    }

    /**
     * 分页查询
     */
    @Override
    public Outcome<List<${classInfo.className}BO>> pageList(${classInfo.className}QueryParam param, PageParam pageParam){
        PageRequest pageRequest = PageUtil.toPageRequest(pageParam);
        Page<${classInfo.className}> page = ${classInfo.className?uncap_first}Repository.findAll(DynamicSpecifications.toSpecification(param), pageRequest);

        List<${classInfo.className}BO> ${classInfo.className?uncap_first}Bos = ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bos(page.getContent());
        return Outcome.success(PageUtil.toPagination(page), ${classInfo.className?uncap_first}Bos);
    }
}