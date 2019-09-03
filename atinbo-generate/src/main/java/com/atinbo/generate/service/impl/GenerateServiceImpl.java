package com.atinbo.generate.service.impl;

import com.atinbo.generate.config.GenerateProperties;
import com.atinbo.generate.core.Constant;
import com.atinbo.generate.core.GenerateUtil;
import com.atinbo.generate.mapper.GenerateMapper;
import com.atinbo.generate.model.ColumnInfo;
import com.atinbo.generate.model.TableInfo;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.generate.vo.ClassInfo;
import com.atinbo.generate.vo.FieldInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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
    private Configuration configuration;
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
        classInfo.setPackageName(generateProperties.getPackageName());
        classInfo.setAuthor(generateProperties.getAuthor());

        String className = tableInfo.getTableName().replaceFirst(generateProperties.getTablePrefix(),"");
        classInfo.setClassName(GenerateUtil.genClassName(className));
        classInfo.setTableName(tableInfo.getTableName());
        classInfo.setClassComment(tableInfo.getTableComment());

        List<ColumnInfo> columnList = generateMapper.selectColumnList(tableInfo.getTableName());
        if(!CollectionUtils.isEmpty(columnList)) {
            List<FieldInfo> fieldInfoList = new ArrayList<>();
            FieldInfo fieldInfo;
            for (ColumnInfo column : columnList) {
                fieldInfo = new FieldInfo();

                fieldInfo.setFieldName(GenerateUtil.underlineToCamelCase(column.getColumnName()));
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

    @Override
    public void generateClass(ClassInfo classInfo) throws IOException, TemplateException {
        Map<String, Object> params = new HashMap<>();
        params.put("classInfo", classInfo);
        String prefixPath = GenerateUtil.genFilePath(generateProperties.getOutPath(),generateProperties.getPackageName());

        processFile(Constant.MODEL_TEMPLATE_PATH, genFilePath(prefixPath, String.format(Constant.MODEL_PATH,classInfo.getClassName())), params);
        processFile(Constant.MAPPER_TEMPLATE_PATH, genFilePath(prefixPath, String.format(Constant.MAPPER_PATH,classInfo.getClassName())), params);
        processFile(Constant.MYBATIS_TEMPLATE_PATH, genFilePath(prefixPath, String.format(Constant.MYBATIS_PATH,classInfo.getClassName())), params);

        processFile(Constant.SERVICE_TEMPLATE_PATH, genFilePath(prefixPath, String.format(Constant.SERVICE_IMPL_PATH,classInfo.getClassName())), params);
        processFile(Constant.SERVICE_IMPL_TEMPLATE_PATH, genFilePath(prefixPath, String.format(Constant.SERVICE_IMPL_PATH,classInfo.getClassName())), params);
        processFile(Constant.CONTROLLER_TEMPLATE_PATH, genFilePath(prefixPath,String.format(Constant.CONTROLLER_PATH,classInfo.getClassName())), params);
    }


    private String genFilePath(String prefix, String path) throws IOException {
        String filePath = prefix + File.separator + path;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return filePath;
    }

    /**
     * process File
     *
     * @param templatePath
     * @param filePath
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public void processFile(String templatePath, String filePath, Map<String, Object> params)
            throws IOException, TemplateException {

        Template template = configuration.getTemplate(templatePath);
        FileWriter writer = new FileWriter(filePath);
        template.process(params, writer);
        writer.close();
    }
}
