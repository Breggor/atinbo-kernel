package com.atinbo.generate.service.impl;

import com.atinbo.generate.config.GenerateConfig;
import com.atinbo.generate.config.RequestThread;
import com.atinbo.generate.core.CategoryEnum;
import com.atinbo.generate.core.FrameworkEnum;
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
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 表查询服务实现
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Slf4j
@Service
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    private Configuration configuration;
    @Autowired
    private GenerateMapper generateMapper;

    @Override
    public List<ClassInfo> findAllTable() {
        GenerateConfig config = RequestThread.getConfig();
        List<ClassInfo> result = new ArrayList<>();
        List<TableInfo> list = generateMapper.selectTableList();
        if (!CollectionUtils.isEmpty(list)) {
            ClassInfo classInfo;
            for (TableInfo tableInfo : list) {
                classInfo = new ClassInfo();
                classInfo.setPackageName(GenerateUtil.getPackageName(config.getPackageName(), config.getModuleName()));
                classInfo.setAuthor(config.getAuthor());

                String className = RegExUtils.replaceFirst(tableInfo.getTableName(), config.getTablePrefix(), "");
                classInfo.setClassName(GenerateUtil.genClassName(className));
                classInfo.setTableName(tableInfo.getTableName());
                classInfo.setClassComment(tableInfo.getTableComment());
                result.add(classInfo);
            }
        }
        return result;
    }

    @Override
    public boolean generateClass(String tableName){
        AtomicBoolean result = new AtomicBoolean(true);
        GenerateConfig config = RequestThread.getConfig();
        ClassInfo classInfo = findClassInfo(tableName);
        String prefixPath = GenerateUtil.genFilePath(config.getOutPath(), classInfo.getPackageName());
        String category = CategoryEnum.check(config.getCategory());
        String framework = FrameworkEnum.check(config.getFramework());

        List<TemplatePathEnum> templateEnums = TemplatePathEnum.getTemplates(framework);
        templateEnums.forEach(t -> {
            //category 为空则是通用的。否则根据类型区分
            if (StringUtils.isBlank(t.getCategory()) || t.getCategory().equals(category)) {
                try {
                    processFile(t, prefixPath, classInfo, category);
                } catch (Exception e) {
                    log.error("生成{}的{}文件失败", classInfo.getClassName(), t.getTemplateName(), e);
                    result.set(false);
                }
            }
        });
        return result.get();
    }

    /**
     * 获取表信息
     * @param tableName
     * @return
     */
    private ClassInfo findClassInfo(String tableName) {
        GenerateConfig config = RequestThread.getConfig();
        TableInfo tableInfo = generateMapper.selectTableInfo(tableName);
        ClassInfo classInfo = new ClassInfo();
        classInfo.setPackageName(GenerateUtil.getPackageName(config.getPackageName(), config.getModuleName()));
        classInfo.setAuthor(config.getAuthor());

        String className = RegExUtils.replaceFirst(tableInfo.getTableName(), config.getTablePrefix(), "");
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
                    if(classInfo.getPrimaryField() != null){
                        log.error("当前表【{}】没有已存在主键信息，目前不支持联合主键", tableName);
                        return null;
                    }
                    fieldInfo.setPrimaryKey(true);
                    classInfo.setPrimaryField(fieldInfo);
                }
                fieldInfoList.add(fieldInfo);
            }
            if(classInfo.getPrimaryField() == null){
                log.error("当前表【{}】没有找到主键信息", tableName);
                return null;
            }
            classInfo.setFieldList(fieldInfoList);
        }
        return classInfo;
    }

    /**
     * 代码生成
     * @param entity 模版枚举
     * @param prefix 生成路径前缀
     * @param classInfo 生成类信息
     * @param category 数据层模版类型
     */
    private void processFile(TemplatePathEnum entity, String prefix, ClassInfo classInfo, String category) throws IOException, TemplateException {
        GenerateConfig config = RequestThread.getConfig();
        Map<String, Object> params = new HashMap<>();
        params.put("classInfo" , classInfo);
        params.put("moduleName" , config.getModuleName());

        StringBuffer modulePath = new StringBuffer();
        if (StringUtils.isNotBlank(config.getModuleName())) {
            modulePath.append(config.getModuleName()).append("-").append(entity.getModule()).append(File.separator);
        }

        String filePath = modulePath.append(prefix).append(File.separator)
                .append(entity.genOutPath(classInfo.getClassName())).toString();
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
