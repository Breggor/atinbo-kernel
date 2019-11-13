package ${classInfo.packageName}.service.impl;

import com.atinbo.mybatis.DynamicCondition;
import com.atinbo.core.exception.CheckErrorException;
import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.mapper.${classInfo.className}Mapper;
import ${classInfo.packageName}.service.${classInfo.className}Service;
import ${classInfo.packageName}.web.form.${classInfo.className}QueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  ${classInfo.classComment}
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Service
public class ${classInfo.className}ServiceImpl implements ${classInfo.className}Service {

    @Autowired
    private ${classInfo.className}Mapper ${classInfo.className?uncap_first}Mapper;

    /**
     * 新增
     */
    @Override
    public int save(${classInfo.className} param) {
        return ${classInfo.className?uncap_first}Mapper.insert(param);
    }

    /**
     * 根据主键查询
     */
    @Override
    public ${classInfo.className} findById(${classInfo.primaryField.fieldClass} id) {
        return ${classInfo.className?uncap_first}Mapper.selectById(id);
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(${classInfo.primaryField.fieldClass} id) {
        return ${classInfo.className?uncap_first}Mapper.deleteById(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(${classInfo.className} entity) {
        if (entity == null || entity.get${classInfo.primaryField.fieldName?cap_first}() == null) {
            throw new CheckErrorException("修改：参数有误");
        }
        return ${classInfo.className?uncap_first}Mapper.updateById(entity);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<${classInfo.className}> findAllByPage(${classInfo.className}QueryForm param) {
        Page<${classInfo.className}> page = new Page(param.getPage(), param.getSize());
        return ${classInfo.className?uncap_first}Mapper.selectPage(page, DynamicCondition.toWrapper(param));
    }
}