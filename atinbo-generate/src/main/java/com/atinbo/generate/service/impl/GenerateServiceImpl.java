package com.atinbo.generate.service.impl;

import com.atinbo.generate.config.GenerateProperties;
import com.atinbo.generate.core.GenerateUtil;
import com.atinbo.generate.mapper.GenerateMapper;
import com.atinbo.generate.model.ColumnInfo;
import com.atinbo.generate.model.TableInfo;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.generate.vo.ClassInfo;
import com.atinbo.generate.vo.FieldInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表查询服务实现
 * @author code-generator
 * @date 2019-8-20
 */
@Service
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    private GenerateProperties generateProperties;

    @Autowired
    private GenerateMapper generateMapper;

    @Override
    public List<ClassInfo> findAllTable() {
        List<ClassInfo> result = new ArrayList<>();
        List<TableInfo> list = generateMapper.selectTableList();
        if(!CollectionUtils.isEmpty(list)) {
            ClassInfo classInfo;
            for (TableInfo tableInfo : list) {
                classInfo = new ClassInfo();
                classInfo.setPackageName(generateProperties.getPackageName());
                classInfo.setAuthor(generateProperties.getAuthor());

                String className = tableInfo.getTableName().replaceFirst(generateProperties.getTablePrefix(),"");
                classInfo.setClassName(GenerateUtil.underlineToCamelCase(className));
                classInfo.setTableName(tableInfo.getTableName());
                classInfo.setClassComment(tableInfo.getTableComment());
                result.add(classInfo);
            }
        }
        return result;
    }

    @Override
    public ClassInfo findClassInfo(String tableName) {
        TableInfo tableInfo = generateMapper.selectTableInfo(tableName);
        ClassInfo classInfo = new ClassInfo();
        classInfo.setPackageName(generateProperties.getPackageName());
        classInfo.setAuthor(generateProperties.getAuthor());

        String className = tableInfo.getTableName().replaceFirst(generateProperties.getTablePrefix(),"");
        classInfo.setClassName(GenerateUtil.underlineToCamelCase(className));
        classInfo.setTableName(tableInfo.getTableName());
        classInfo.setClassComment(tableInfo.getTableComment());

        List<ColumnInfo> columnList = generateMapper.selectColumnList(tableInfo.getTableName());
        if(!CollectionUtils.isEmpty(columnList)) {
            List<FieldInfo> fieldInfoList = new ArrayList<>();
            FieldInfo fieldInfo;
            for (ColumnInfo column : columnList) {
                fieldInfo = new FieldInfo();

                fieldInfo.setColumnName(column.getColumnName());
                fieldInfo.setFieldComment(column.getColumnComment());
                fieldInfo.setFieldClass(GenerateUtil.getJavaClass(column.getDataType()));

                if("PRI".equalsIgnoreCase(column.getColumnKey())){
                    fieldInfo.setPrimaryKey(true);
                    classInfo.setPrimaryField(fieldInfo);
                }
                fieldInfoList.add(fieldInfo);
            }
            classInfo.setFieldList(fieldInfoList);
        }
        return classInfo;
    }

}
