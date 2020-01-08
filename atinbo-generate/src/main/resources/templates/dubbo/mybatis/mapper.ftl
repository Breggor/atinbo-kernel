package ${classInfo.packageName}.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ${classInfo.packageName}.entity.${classInfo.className};

/**
 *  ${classInfo.classComment}
 *
 *  @author ${classInfo.author}
 *  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Mapper
public interface ${classInfo.className}Mapper extends BaseMapper<${classInfo.className}> {

}
