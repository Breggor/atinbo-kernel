package com.atinbo.generate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zenghao
 * @date 2019-09-03
 */
@Getter
@AllArgsConstructor
public enum TemplatePathEnum {

    ENTITY("/entity.ftl", "entity/%s.java" , "service", ""),
    MAPPER("/mapper.ftl", "mapper/%sMapper.java", "service", ""),
    DAO("/dao.ftl", "dao/%sDao.java","service", "mybatis"),
    MYBATIS("/mybatis.ftl", "mybatis/%sDao.xml", "service", "mybatis"),
    REPOSITORY("/repository.ftl", "repository/%sRepository.java", "service", "hibernate"),
    BO("/bo.ftl", "model/%sBO.java", "api", ""),
    PARAM("/param.ftl", "model/%sParam.java", "api", ""),
    SERVICE("/service.ftl", "service/%sService.java", "api", ""),
    IMPL("/impl.ftl", "impl/%sServiceImpl.java", "service", ""),
    VO("/vo.ftl", "openapi/model/%sVO.java", "openapi", ""),
    FORM("/form.ftl", "openapi/model/%sForm.java", "openapi", ""),
    OPENAPI_MAPPER("/openapi_mapper.ftl", "openapi/mapper/%sMapper.java", "openapi", ""),
    CONTROLLER("/controller.ftl", "openapi/controller/%sController.java", "openapi", "");

    private String templatePath;
    private String outPath;
    private String module;
    private String categroy;

    public String genOutPath(String className){
        return String.format(getOutPath(), className);
    }

    public String genTemplatePath(String categroy){
        return categroy.concat(getTemplatePath());
    }
}
