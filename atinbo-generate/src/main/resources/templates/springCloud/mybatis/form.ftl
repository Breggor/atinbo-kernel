package ${classInfo.packageName}.web.form;

import ${classInfo.packageName}.entity.${classInfo.className};
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *  ${classInfo.classComment} 入参参数
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Accessors(chain = true)
public class ${classInfo.className}QueryForm extends ${classInfo.className} {

    /**
     * 当前页
     */
    @ApiModelProperty("起始页")
    private int page;

    /**
     * 每页行数
     */
    @ApiModelProperty("每页行数" )
    private int size = 10;


}