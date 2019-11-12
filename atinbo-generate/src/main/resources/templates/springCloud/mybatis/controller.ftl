package ${classInfo.packageName}.web.controller;

import com.atinbo.core.exception.BizErrorException;
import com.atinbo.model.ErrorInfo;
import com.atinbo.model.Outcome;
import com.atinbo.model.Pagable;
import ${classInfo.packageName}.entity.${classInfo.className};
import ${classInfo.packageName}.service.${classInfo.className}Service;
import ${classInfo.packageName}.web.form.${classInfo.className}QueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *  ${classInfo.classComment}
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Api(tags = "${classInfo.classComment}")
@RestController
@RequestMapping("/${classInfo.className?uncap_first}s")
public class ${classInfo.className}Controller {

    @Autowired
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;

    /**
     * 新增 ${classInfo.classComment}
     *
     * @return
     */
    @ApiOperation(value = "新增${classInfo.classComment}")
    @HttpApiResponse
    @PostMapping
    public Outcome create(@RequestBody @Validated @ApiParam("${classInfo.classComment}") ${classInfo.className} param) {
        return Outcome.status(${classInfo.className?uncap_first}Service.save(param));
    }

    /**
     * 根据主键查询 ${classInfo.classComment}
     *
     * @return
     */
    @ApiOperation(value = "根据id查询${classInfo.classComment}")
    @HttpApiResponse
    @GetMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "主键id", paramType = "path", required = true, dataTypeClass = ${classInfo.primaryField.fieldClass}.class)
    public Outcome<${classInfo.className}> get(@PathVariable @Validated ${classInfo.primaryField.fieldClass} id) {
        ${classInfo.className} ${classInfo.className?uncap_first} = ${classInfo.className?uncap_first}Service.findById(id);
        return Outcome.success(${classInfo.className?uncap_first});
    }

    /**
     * 根据条件查询并分页
     *
     * @return
     */
    @ApiOperation(value = "${classInfo.classComment}分页查询")
    @HttpApiResponse
    @PostMapping("/search")
    public Outcome<Pagable<${classInfo.className}>> page(@RequestBody @ApiParam("分页查询") ${classInfo.className}QueryForm param) {
        IPage<${classInfo.className}> page = ${classInfo.className?uncap_first}Service.findAllByPage(param);
        return Outcome.success(Pagable.of(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), page.getRecords()));
    }

    /**
     * 修改 ${classInfo.classComment}
     *
     * @return
     */
    @ApiOperation(value = "修改${classInfo.classComment}")
    @HttpApiResponse
    @PutMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "主键id", paramType = "path", required = true, dataTypeClass = ${classInfo.primaryField.fieldClass}.class)
    public Outcome<${classInfo.className}> modify(@PathVariable ${classInfo.primaryField.fieldClass} id, @ApiParam("修改${classInfo.classComment}") @RequestBody ${classInfo.className} param) {
        int opt = ${classInfo.className?uncap_first}Service.update(param);
        if (opt <= 0) {
            throw new BizErrorException("修改失败");
        }
        return Outcome.success(param);
    }


    /**
     * 根据主键id删除 ${classInfo.classComment}
     *
     * @return
     */
    @ApiOperation(value = "删除${classInfo.classComment}")
    @HttpApiResponse
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "主键id", paramType = "path", required = true, dataTypeClass = ${classInfo.primaryField.fieldClass}.class)
    public Outcome remove(@PathVariable ${classInfo.primaryField.fieldClass} id) {
        return Outcome.status(${classInfo.className?uncap_first}Service.deleteById(userId));
    }

}
