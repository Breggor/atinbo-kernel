package com.atinbo.generate.controller;

import com.atinbo.core.utils.BeanUtil;
import com.atinbo.generate.config.GenerateConfig;
import com.atinbo.generate.config.RequestThread;
import com.atinbo.generate.model.ClassInfo;
import com.atinbo.generate.model.GenForm;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.model.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zenghao
 * @date 2019-09-02
 */
@Slf4j
@RestController
public class GenerarteController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private GenerateService generateService;

    @GetMapping("/gen/index")
    public Map<String,Object> index() {
        GenerateConfig config = GenerateConfig.defaultConfig();
        config.setModuleName(applicationName);
        RequestThread.setConfig(config);
        List<ClassInfo> classInfos = generateService.findAllTable();
        Map<String, Object> map = new HashMap<>();
        map.put("classInfos", classInfos);
        map.put("properties", config);
        return map;
    }

    @PostMapping("/gen")
    public Outcome gen(String tableName,GenerateConfig config) {
        if(StringUtils.isBlank(config.getOutPath())){
            config.setOutPath(GenerateConfig.DEFAULT_OUT_PATH);
        }
        RequestThread.setConfig(config);

        List<String> error = new ArrayList<>();
        if (StringUtils.isNotBlank(tableName)) {
            String[] tables = StringUtils.split(tableName, ",");
            for (String table : tables) {
                try {
                    boolean flag = generateService.generateClass(tableName);
                    if(!flag){
                        error.add(table);
                        continue;
                    }
                } catch (Exception e) {
                    log.error("gen table:{} error:" , table, e);
                    error.add(table);
                    continue;
                }
            }
        } else {
            List<ClassInfo> classInfoList = generateService.findAllTable();
            for (ClassInfo classInfo : classInfoList) {
                try {
                    boolean flag = generateService.generateClass(classInfo.getTableName());
                    if(!flag){
                        error.add(classInfo.getTableName());
                        continue;
                    }
                } catch (Exception e) {
                    log.error("gen all table:{} error:" , classInfo.getTableName(), e);
                    error.add(classInfo.getTableName());
                    continue;
                }
            }
        }
        return error.isEmpty() ? Outcome.success() : Outcome.failure(String.join("," ,error) + "生成失败");
    }

}
