package com.atinbo.generate.controller;

import com.atinbo.core.http.model.ResultVO;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.generate.vo.ClassInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zenghao
 * @date 2019-09-02
 */
@Slf4j
@RestController
public class GenerarteController {

    @Autowired
    private GenerateService generateService;


    @GetMapping("/gen/list")
    public List<ClassInfo> list() {
        return generateService.findAllTable();
    }

    @PostMapping("/gen")
    public ResultVO gen(@RequestParam(name = "tableName", required = false) String tableName) {
        if (StringUtils.isNotBlank(tableName)) {
            if(StringUtils.contains(tableName,",")){
                String[] tables = StringUtils.split(tableName,",");
                for (String table : tables) {
                    ClassInfo classInfo = generateService.findClassInfo(table);
                    try {
                        generateService.generateClass(classInfo);
                    } catch (Exception e) {
                        log.error("gen table:{} error:", table, e);
                    }
                }
            }else {
                ClassInfo classInfo = generateService.findClassInfo(tableName);
                try {
                    generateService.generateClass(classInfo);
                } catch (Exception e) {
                    log.error("gen table:{} error:", tableName, e);
                }
            }
        } else {
            List<ClassInfo> classInfoList = generateService.findAllTable();
            for (ClassInfo classInfo : classInfoList) {
                try {
                    generateService.generateClass(classInfo);
                } catch (Exception e) {
                    log.error("gen table error:", e);
                    continue;
                }
            }
        }

        return ResultVO.success();
    }

}
