package com.atinbo.generate.service;

import com.atinbo.generate.model.ClassInfo;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;

/**
 * 表查询服务接口
 *
 * @author code-generator
 * @date 2019-8-20
 */
public interface GenerateService {

    /**
     * 查询所有的表对象
     *
     * @return
     */
    List<ClassInfo> findAllTable();

    /**
     * 生成代码
     *
     * @param tableName
     * @return
     */
    boolean generateClass(String tableName);
}
