package ${classInfo.packageName}.service;

import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.web.form.${classInfo.className}QueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  ${classInfo.classComment} Service
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
public interface ${classInfo.className}Service {

    /**
     * 新增
     *
     * @param param
     * @return
     */
    int save(${classInfo.className} param);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ${classInfo.className} findById(${classInfo.primaryField.fieldClass} id);

    /**
     * 分页查询所有用户
     *
     * @param param
     * @return
     */
    IPage<${classInfo.className}> findAllByPage(${classInfo.className}QueryForm param);

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    int deleteById(${classInfo.primaryField.fieldClass} id);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    int update(${classInfo.className} entity);
}
