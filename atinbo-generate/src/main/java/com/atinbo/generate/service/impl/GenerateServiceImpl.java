package com.atinbo.generate.service.impl;

import com.atinbo.generate.config.GenerateProperties;
import com.atinbo.generate.core.GenerateUtil;
import com.atinbo.generate.core.TemplatePathEnum;
import com.atinbo.generate.mapper.GenerateMapper;
import com.atinbo.generate.model.ClassInfo;
import com.atinbo.generate.model.ColumnInfo;
import com.atinbo.generate.model.FieldInfo;
import com.atinbo.generate.model.TableInfo;
import com.atinbo.generate.service.GenerateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atinbo.generate.core.GenerateUtil.genFilePath;

/**
 * 表查询服务实现
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Service
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private Configuration configuration;
    @Autowired
    private GenerateMapper generateMapper;

    @Override
    public List<ClassInfo> findAllTable() {
        List<ClassInfo> result = new ArrayList<>();
        List<TableInfo> list = generateMapper.selectTableList();
        if (!CollectionUtils.isEmpty(list)) {
            ClassInfo classInfo;
            for (TableInfo tableInfo : list) {
                classInfo = new ClassInfo();
                classInfo.setPackageName(GenerateUtil.getPackageName(generateProperties.getPackageName(), generateProperties.getModule().getName()));
                classInfo.setAuthor(generateProperties.getAuthor());

                String className = RegExUtils.replaceFirst(tableInfo.getTableName(), generateProperties.getTablePrefix(), "");
                classInfo.setClassName(GenerateUtil.genClassName(className));
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
        classInfo.setPackageName(GenerateUtil.getPackageName(generateProperties.getPackageName(), generateProperties.getModule().getName()));
        classInfo.setAuthor(generateProperties.getAuthor());

        String className = RegExUtils.replaceFirst(tableInfo.getTableName(), generateProperties.getTablePrefix(), "");
        classInfo.setClassName(GenerateUtil.genClassName(className));
        classInfo.setTableName(tableInfo.getTableName());
        classInfo.setClassComment(tableInfo.getTableComment());

        List<ColumnInfo> columnList = generateMapper.selectColumnList(tableInfo.getTableName());
        if (!CollectionUtils.isEmpty(columnList)) {
            List<FieldInfo> fieldInfoList = new ArrayList<>();
            FieldInfo fieldInfo;
            for (ColumnInfo column : columnList) {
                fieldInfo = new FieldInfo();

                fieldInfo.setFieldName(GenerateUtil.underlineToCamelCase(column.getColumnName()));
                fieldInfo.setColumnName(column.getColumnName());
                fieldInfo.setFieldComment(column.getColumnComment());
                fieldInfo.setFieldClass(GenerateUtil.getJavaClass(column.getDataType()));

                if ("PRI".equalsIgnoreCase(column.getColumnKey())) {
                    fieldInfo.setPrimaryKey(true);
                    classInfo.setPrimaryField(fieldInfo);
                }
                fieldInfoList.add(fieldInfo);
            }
            classInfo.setFieldList(fieldInfoList);
        }
        return classInfo;
    }

    @Override
    public void generateClass(ClassInfo classInfo) throws IOException, TemplateException {
        String prefixPath = genFilePath(generateProperties.getOutPath(), classInfo.getPackageName());
        String category = generateProperties.getCategory();
        if (!ArrayUtils.contains(GenerateUtil.SUPPORT_CATEGORY, category)) {
            category = GenerateUtil.SUPPORT_CATEGORY[0];
        }

        for (TemplatePathEnum pathEnum : TemplatePathEnum.values()) {
            if (StringUtils.isBlank(pathEnum.getCategory()) || pathEnum.getCategory().equals(category)) {
                processFile(pathEnum, prefixPath, classInfo, category);
            }
        }
    }

    private void processFile(TemplatePathEnum entity, String prefix, ClassInfo classInfo, String category) throws IOException, TemplateException {
        Map<String, Object> params = new HashMap<>();
        params.put("classInfo" , classInfo);

        StringBuffer modulePath = new StringBuffer("");
        if (generateProperties.getModule() != null) {
            if (StringUtils.isNotBlank(generateProperties.getModule().getName())) {
                modulePath.append(generateProperties.getModule().getName());
            }
            if (generateProperties.getModule().isMultiple()) {
                if (modulePath.length() > 0) {
                    modulePath.append("-");
                }
                modulePath.append(entity.getModule());
            }
            if (modulePath.length() > 0) {
                modulePath.append(File.separator);
            }
        }
        String filePath = modulePath.append(prefix).append(File.separator).append(entity.genOutPath(classInfo.getClassName())).toString();
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        Template template = configuration.getTemplate(entity.genTemplatePath(category));
        FileWriter writer = new FileWriter(filePath);
        template.process(params, writer);
        writer.close();
    }

}
