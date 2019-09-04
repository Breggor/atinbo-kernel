package ${classInfo.packageName}.openapi.controller;

import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import com.atinbo.core.exception.HttpAPIException;
import com.atinbo.core.http.model.Result;
import com.atinbo.core.http.model.PageResult;
import com.atinbo.core.http.status.HttpStatusCode;
import com.atinbo.model.Outcome;
import com.atinbo.model.PageOutcome;

import ${classInfo.packageName}.openapi.model.${classInfo.className}Form;
import ${classInfo.packageName}.openapi.model.${classInfo.className}VO;
import ${classInfo.packageName}.openapi.mapper.${classInfo.className}Mapper;
import ${classInfo.packageName}.model.${classInfo.className}Param;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.service.${classInfo.className}Service;

/**
*  ${classInfo.classComment}
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Api(value = "${classInfo.className?uncap_first}", tags = "${classInfo.classComment}")
@RestController
@RequestMapping("/${classInfo.className?uncap_first}")
public class ${classInfo.className}Controller {

    @Reference
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;

    /**
     * 根据条件查询并分页
     *
     * @return
     * @throws HttpAPIException
     */
    @ApiOperation(value = "${classInfo.classComment}分页查询")
    @ApiResponses(@ApiResponse(code = 500001, message = "系统错误"))
    @GetMapping
    public PageResult findPage(@Validated @ApiParam("${classInfo.classComment}查询参数") ${classInfo.className}Form form) throws HttpAPIException {
        PageOutcome outcome = ${classInfo.className?uncap_first}Service.pageList(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Param(form));

        if (outcome.isSuccess()) {
            return PageResult.of(outcome.getPage(), ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Vos(outcome.getData()));
        } else {
            throw new HttpAPIException(HttpStatusCode.ERR_500);
        }
    }

    /**
    * 根据主键查询 ${classInfo.classComment}
    *
    * @return
    * @throws HttpAPIException
    */
    @ApiOperation(value = "根据id查询${classInfo.classComment}")
    @ApiResponses(@ApiResponse(code = 500001, message = "系统错误"))
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") @Validated @ApiParam("${classInfo.primaryField.fieldComment}") ${classInfo.primaryField.fieldClass} id) throws HttpAPIException {
        Outcome<${classInfo.className}BO> outcome = ${classInfo.className?uncap_first}Service.findById(id);
        if (outcome.isSuccess()) {
            return ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Vo(outcome.getData());
        } else {
            throw new HttpAPIException(HttpStatusCode.ERR_500);
        }
    }

    /**
     * 新增 ${classInfo.classComment}
     *
     * @return
     * @throws HttpAPIException
     */
    @ApiOperation(value = "添加${classInfo.classComment}")
    @ApiResponses(@ApiResponse(code = 500001, message = "系统错误"))
    @PostMapping
    public Result add(@RequestBody @Validated @ApiParam("${classInfo.classComment}信息") ${classInfo.className}Form form) throws HttpAPIException {
        Outcome<${classInfo.className}BO> outcome = ${classInfo.className?uncap_first}Service.save(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Param(form));
        if (outcome.isSuccess()) {
            return ${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Vo(outcome.getData());
        } else {
            throw new HttpAPIException(HttpStatusCode.ERR_500);
        }
    }

    /**
    * 修改 ${classInfo.classComment}
    *
    * @return
    * @throws HttpAPIException
    */
    @ApiOperation(value = "修改${classInfo.classComment}")
    @ApiResponses(@ApiResponse(code = 500001, message = "系统错误"))
    @PutMapping
    public Result edit(@RequestBody @Validated @ApiParam("${classInfo.classComment}信息") ${classInfo.className}Form form) throws HttpAPIException {
        boolean flag = ${classInfo.className?uncap_first}Service.update(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Param(form));
        if (flag) {
            return Result.success();
        } else {
            throw new HttpAPIException(HttpStatusCode.ERR_500);
        }
    }

    /**
     * 根据主键id删除 ${classInfo.classComment}
     *
     * @return
     * @throws HttpAPIException
     */
    @ApiOperation(value = "根据id删除${classInfo.classComment}")
    @ApiResponses(@ApiResponse(code = 500001, message = "系统错误"))
    @GetMapping("/{id}")
    public Result deleteById(@PathVariable("id") @Validated @ApiParam("${classInfo.primaryField.fieldComment}") ${classInfo.primaryField.fieldClass} id) throws HttpAPIException {
        boolean flag = ${classInfo.className?uncap_first}Service.deleteById(id);
        if (flag) {
            return Result.success();
        } else {
            throw new HttpAPIException(HttpStatusCode.ERR_500);
        }
    }
}
