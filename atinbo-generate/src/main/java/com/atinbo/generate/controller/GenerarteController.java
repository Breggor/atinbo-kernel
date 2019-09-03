package com.atinbo.generate.controller;

import com.atinbo.core.http.model.Result;
import com.atinbo.generate.core.Constant;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.generate.vo.ClassInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zenghao
 * @date 2019-09-02
 */
@RestController
public class GenerarteController {

    @Autowired
    private GenerateService generateService;


    @GetMapping("/gen/list")
    public List<ClassInfo> list(){
        return generateService.findAllTable();
    }

    @PostMapping("/gen")
    public Result gen(@RequestParam(name = "tableName",required = false)String tableName){
        if(StringUtils.isNotBlank(tableName)){
            ClassInfo classInfo = generateService.findClassInfo(tableName);
            try {
                generateService.generateClass(classInfo);
            } catch (Exception e) {
            }
        }else {
            List<ClassInfo> classInfoList = generateService.findAllTable();
            for (ClassInfo classInfo : classInfoList) {
                try {
                    generateService.generateClass(classInfo);
                }catch (Exception e){
                    continue;
                }
            }
        }

        return Result.success();
    }

}
