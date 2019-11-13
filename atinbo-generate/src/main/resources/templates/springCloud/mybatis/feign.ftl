package ${classInfo.packageName}.feign;

import com.atinbo.model.Outcome;
import com.atinbo.model.Pagable;
import ${classInfo.packageName}.fallback.${classInfo.className}ClientFallback;
import ${classInfo.packageName}.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *  ${classInfo.classComment} 接口
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@FeignClient(name = "${moduleName}-service", fallback = ${classInfo.className}ClientFallback.class)
public interface I${classInfo.className}Client {

    /**
     * 创建${classInfo.classComment}
     *
     * @param param
     * @return
     */
    @PostMapping("/${classInfo.className?uncap_first}s")
    Outcome create(@RequestBody ${classInfo.className}Param param);

    /**
     * 用户根据分页查找
     *
     * @param param
     * @return
     */
    @GetMapping("/${classInfo.className?uncap_first}s/search")
    Outcome<Pagable<${classInfo.className}DTO>> findAllByPage(@RequestBody ${classInfo.className}QueryParam param);

    /**
     * 用户根据id进行查找
     *
     * @param id
     * @return
     */
    @GetMapping("/${classInfo.className?uncap_first}s/{id}")
    Outcome<${classInfo.className}DTO> findById(@PathVariable(value = "id") ${classInfo.primaryField.fieldClass} id);

    /**
     * 根据id修改
     *
     * @param id
     * @param param
     * @return
     */
    @PutMapping("/${classInfo.className?uncap_first}s/{id}")
    Outcome<${classInfo.className}DTO> modify(@PathVariable(value = "id") ${classInfo.primaryField.fieldClass} id, @RequestBody ${classInfo.className}Param param);

    /**
     * 根据id进行删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/${classInfo.className?uncap_first}/{id}")
    boolean deleteById(@PathVariable(value = "id") ${classInfo.primaryField.fieldClass} id);
}