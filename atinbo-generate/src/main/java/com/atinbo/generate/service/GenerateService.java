package com.atinbo.generate.service;

import com.atinbo.generate.vo.ClassInfo;

import java.util.List;

/**
 * 表查询服务接口
 * @author code-generator
 * @date 2019-8-20
 */
public interface GenerateService {

    /**
     * 查询所有的表对象
     * @return
     */
    List<ClassInfo> findAllTable();

    /**
     * 获取完整的单表的类对象（包含表中的字段）
     * @param tableName
     */
    ClassInfo findClassInfo(String tableName);
}
