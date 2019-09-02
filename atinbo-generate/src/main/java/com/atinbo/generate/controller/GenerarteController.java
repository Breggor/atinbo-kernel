package com.atinbo.generate.controller;

import com.atinbo.generate.service.GenerateService;
import com.atinbo.generate.vo.ClassInfo;
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
    public void gen(@RequestParam(name = "table",required = false)String tableName){
        generateService.findClassInfo(tableName);
    }
}
