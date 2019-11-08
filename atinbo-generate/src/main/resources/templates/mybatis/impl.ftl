package ${classInfo.packageName}.impl;

import com.atinbo.model.Outcome;
import com.atinbo.model.PageParam;
import com.atinbo.model.Pageable;
import com.atinbo.mybatis.DynamicCondition;
import com.atinbo.mybatis.utils.PageUtil;
import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.model.${classInfo.className}Param;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.mapper.${classInfo.className}Mapper;
import ${classInfo.packageName}.dao.${classInfo.className}Dao;
import ${classInfo.packageName}.service.${classInfo.className}Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        return Outcome.success(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bo(${classInfo.className?uncap_first}));
    }

    /**
     * 分页查询
     */
    @Override
    public Outcome<Pageable<${classInfo.className}BO>> pageList(${classInfo.className}Param param, PageParam pageParam){
        Page<${classInfo.className}> page = PageUtil.toPage(pageParam);
        IPage<${classInfo.className}> pageData = ${classInfo.className?uncap_first}Dao.selectPage(page, DynamicCondition.toWrapper(param));

        return Outcome.success(PageUtil.toPageable(pageData, ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Bos(pageData.getRecords())));
    }
}