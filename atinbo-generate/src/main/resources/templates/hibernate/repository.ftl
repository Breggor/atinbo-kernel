package ${classInfo.packageName}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ${classInfo.packageName}.entity.${classInfo.className};

/**
*  ${classInfo.classComment} Repository
*
*  @author ${classInfo.author}
*  @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Repository
public interface ${classInfo.className}Repository extends JpaRepository<${classInfo.className}, ${classInfo.primaryField.fieldClass}>, JpaSpecificationExecutor<${classInfo.className}> {

}
