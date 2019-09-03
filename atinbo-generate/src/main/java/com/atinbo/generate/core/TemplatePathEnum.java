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
    MYBATIS("mybatis.ftl", "mybatis/%sMapper.xml"),
    BO("bo.ftl", "model/%sBO.java"),
    PARAM("param.ftl", "model/%sParam.java"),
    SERVICE("service.ftl", "api/%sService.java"),
    IMPL("impl.ftl", "service/%sServiceImpl.java"),
    VO("vo.ftl", "vo/%sVO.java"),
    FORM("form.ftl", "vo/%sForm.java"),
    CONTROLLER("controller.ftl", "controller/%sController.java");

    private String templatePath;
    private String outPath;

    public static String genOutPath(TemplatePathEnum pathEnum, String className) {
        return String.format(pathEnum.getOutPath(), className);
    }
}
