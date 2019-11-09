package ${classInfo.packageName}.openapi.controller;

import com.atinbo.core.exception.HttpApiException;
import com.atinbo.core.model.PageForm;
import com.atinbo.model.Outcome;
import com.atinbo.model.PageParam;
import com.atinbo.model.Pageable;
import com.atinbo.model.StatusCodeEnum;
import com.atinbo.swagger.annotation.HttpApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ${classInfo.packageName}.openapi.model.${classInfo.className}Form;
import ${classInfo.packageName}.openapi.model.${classInfo.className}QueryForm;
import ${classInfo.packageName}.openapi.model.${classInfo.className}VO;
import ${classInfo.packageName}.openapi.mapper.${classInfo.className}Mapper;
import ${classInfo.packageName}.model.${classInfo.className}BO;
import ${classInfo.packageName}.service.${classInfo.className}Service;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * @throws HttpApiException
     */
    @ApiOperation(value = "${classInfo.classComment}分页查询")
    @HttpApiResponse
    @GetMapping
    public Outcome<Pageable<${classInfo.className}VO>> findPage(@Validated @ApiParam("${classInfo.classComment}查询参数") ${classInfo.className}QueryForm form, PageForm pageForm) throws HttpApiException {
        Outcome<Pageable<${classInfo.className}BO>> outcome = ${classInfo.className?uncap_first}Service.pageList(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Param(form),
            PageParam.of(pageForm.getOffset(), pageForm.getLimit()));

        if (outcome.ok()) {
            return Outcome.success(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Vos(outcome.getData()));
        } else {
            throw new HttpApiException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键查询 ${classInfo.classComment}
     *
     * @return
     * @throws HttpApiException
     */
    @ApiOperation(value = "根据id查询${classInfo.classComment}")
    @HttpApiResponse
    @GetMapping("/{id}")
    public Outcome<${classInfo.className}VO> findById(@PathVariable("id") @Validated @ApiParam("${classInfo.primaryField.fieldComment}") ${classInfo.primaryField.fieldClass} id) throws HttpApiException {
        Outcome<${classInfo.className}BO> outcome = ${classInfo.className?uncap_first}Service.findById(id);
        if (outcome.ok()) {
            return Outcome.success(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Vo(outcome.getData()));
        } else {
            throw new HttpApiException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增 ${classInfo.classComment}
     *
     * @return
     * @throws HttpApiException
     */
    @ApiOperation(value = "添加${classInfo.classComment}")
    @HttpApiResponse
    @PostMapping
    public Outcome add(@RequestBody @Validated @ApiParam("${classInfo.classComment}信息") ${classInfo.className}Form form) throws HttpApiException {
        Outcome<${classInfo.className}BO> outcome = ${classInfo.className?uncap_first}Service.save(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Param(form));
        if (outcome.ok()) {
            return Outcome.success();
        } else {
            throw new HttpApiException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改 ${classInfo.classComment}
     *
     * @return
     * @throws HttpApiException
     */
    @ApiOperation(value = "修改${classInfo.classComment}")
    @HttpApiResponse
    @PutMapping
    public Outcome edit(@RequestBody @Validated @ApiParam("${classInfo.classComment}信息") ${classInfo.className}Form form) throws HttpApiException {
        boolean flag = ${classInfo.className?uncap_first}Service.update(${classInfo.className}Mapper.INSTANCE.to${classInfo.className}Param(form));
        if (flag) {
            return Outcome.success();
        } else {
            throw new HttpApiException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键id删除 ${classInfo.classComment}
     *
     * @return
     * @throws HttpApiException
     */
    @ApiOperation(value = "根据id删除${classInfo.classComment}")
    @HttpApiResponse
    @DeleteMapping("/{id}")
    public Outcome deleteById(@PathVariable("id") @Validated @ApiParam("${classInfo.primaryField.fieldComment}") ${classInfo.primaryField.fieldClass} id) throws HttpApiException {
        boolean flag = ${classInfo.className?uncap_first}Service.deleteById(id);
        if (flag) {
            return Outcome.success();
        } else {
            throw new HttpApiException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
