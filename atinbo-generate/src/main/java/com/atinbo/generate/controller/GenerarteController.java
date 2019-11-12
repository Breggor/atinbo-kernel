package com.atinbo.generate.controller;

import com.atinbo.core.utils.BeanUtil;
import com.atinbo.generate.config.GenerateConfig;
import com.atinbo.generate.config.RequestThread;
import com.atinbo.generate.core.FrameworkEnum;
import com.atinbo.generate.model.ClassInfo;
import com.atinbo.generate.model.GenForm;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.model.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private GenerateService generateService;

    @GetMapping("/gen/index")
    public Map<String,Object> index() {
        GenerateConfig config = GenerateConfig.defaultConfig();
        RequestThread.setConfig(config);
        List<ClassInfo> classInfos = generateService.findAllTable();
        Map<String, Object> map = new HashMap<>();
        map.put("classInfos", classInfos);
        map.put("properties", config);
        return map;
    }

    @PostMapping("/gen")
    public Outcome gen(GenForm genForm) {
        GenerateConfig config = GenerateConfig.defaultConfig();
        config.setAuthor(genForm.getAuthor()).setCategory(genForm.getCategory()).setFramework(genForm.getFramework())
                .setPackageName(genForm.getPackageName()).setTablePrefix(genForm.getTablePrefix())
                .setModuleName(genForm.getModuleName());
        RequestThread.setConfig(config);

        if (StringUtils.isNotBlank(genForm.getTableName())) {
            String[] tables = StringUtils.split(genForm.getTableName(), ",");
            for (String table : tables) {
                ClassInfo classInfo = generateService.findClassInfo(table);
                try {
                    generateService.generateClass(classInfo);
                } catch (Exception e) {
                    log.error("gen table:{} error:" , table, e);
                    continue;
                }
            }
        } else {
            List<ClassInfo> classInfoList = generateService.findAllTable();
            for (ClassInfo classInfo : classInfoList) {
                try {
                    generateService.generateClass(classInfo);
                } catch (Exception e) {
                    log.error("gen all table:{} error:" , classInfo.getTableName(), e);
                    continue;
                }
            }
        }
        return Outcome.success();
    }

    public static void main(String[] args) {
        GenerateConfig param = new GenerateConfig();

        param.setAuthor("aaa");
        GenerateConfig config = GenerateConfig.defaultConfig();
        BeanUtil.copyProperties(param, config);

        System.out.println(BeanUtil.toMap(config));
    }

}
