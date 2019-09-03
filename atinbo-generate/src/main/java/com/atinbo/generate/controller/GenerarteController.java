package com.atinbo.generate.controller;

import com.atinbo.core.http.model.Result;
import com.atinbo.generate.service.GenerateService;
import com.atinbo.generate.vo.ClassInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
