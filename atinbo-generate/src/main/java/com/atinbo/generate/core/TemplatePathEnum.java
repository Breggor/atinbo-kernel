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

    ENTITY("entity.ftl", "entity/%s.java"),
    MAPPER("mapper.ftl", "mapper/%sMapper.java"),
//    DAO("dao.ftl", "dao/%sDao.java"),
//    MYBATIS("mybatis.ftl", "mybatis/%sDao.xml"),
    REPOSITORY("repository.ftl", "repository/%sRepository.java"),
    BO("bo.ftl", "model/%sBO.java"),
    PARAM("param.ftl", "model/%sParam.java"),
    SERVICE("service.ftl", "service/%sService.java"),
    IMPL("impl.ftl", "impl/%sServiceImpl.java"),
    VO("vo.ftl", "openapi/model/%sVO.java"),
    FORM("form.ftl", "openapi/model/%sForm.java"),
    OPENAPI_MAPPER("openapi_mapper.ftl", "openapi/mapper/%sMapper.java"),
    CONTROLLER("controller.ftl", "openapi/controller/%sController.java");

    private String templatePath;
    private String outPath;

    public static String genOutPath(TemplatePathEnum pathEnum, String className) {
        return String.format(pathEnum.getOutPath(), className);
    }
}
