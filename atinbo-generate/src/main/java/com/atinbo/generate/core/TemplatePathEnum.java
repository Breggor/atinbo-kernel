package com.atinbo.generate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zenghao
 * @date 2019-09-03
 */
@Getter
@AllArgsConstructor
public enum TemplatePathEnum {
    
    /*********************************************** DUBBO *************************************************/
    /************* API *************/
    D_BO("/bo.ftl" , "model/%sBO.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_PARAM("/param.ftl" , "model/%sParam.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_QUERY_PARAM("/query_param.ftl" , "model/%sQueryParam.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_SERVICE("/service.ftl" , "service/%sService.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),

    /************* SERVICE *************/
    D_ENTITY("/entity.ftl" , "entity/%s.java" ,
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_MAPPER("/mapper.ftl" , "mapper/%sMapper.java" ,
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_DAO("/dao.ftl" , "dao/%sDao.java" ,
            GenerateUtil.MODULE_SERVICE , CategoryEnum.MYBATIS.getValue(), FrameworkEnum.DUBBO.getValue()),
    D_MYBATIS("/mybatis.ftl" , "mybatis/%sDao.xml" ,
            GenerateUtil.MODULE_SERVICE , CategoryEnum.MYBATIS.getValue(), FrameworkEnum.DUBBO.getValue()),
    D_REPOSITORY("/repository.ftl" , "repository/%sRepository.java" ,
            GenerateUtil.MODULE_SERVICE , CategoryEnum.HIBERNATE.getValue(), FrameworkEnum.DUBBO.getValue()),
    D_IMPL("/impl.ftl" , "impl/%sServiceImpl.java" ,
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),

    /************* OPENAPI *************/
    D_VO("/vo.ftl" , "openapi/model/%sVO.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_FORM("/form.ftl" , "openapi/model/%sForm.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_QUERY_FORM("/query_form.ftl" , "openapi/model/%sQueryForm.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_OPENAPI_MAPPER("/openapi_mapper.ftl" , "openapi/mapper/%sMapper.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    D_CONTROLLER("/controller.ftl" , "openapi/controller/%sController.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),

    /*********************************************** SPRING_CLOUD *************************************************/

    /************* SERVICE *************/
    S_ENTITY("/entity.ftl","entity/%s.java",
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_MAPPER("/mapper.ftl","mapper/%sMapper.java",
            GenerateUtil.MODULE_SERVICE , CategoryEnum.MYBATIS.getValue(), FrameworkEnum.SPRING_CLOUD.getValue()),
    S_MYBATIS("/mybatis.ftl","mybatis/%sMapper.xml",
            GenerateUtil.MODULE_SERVICE , CategoryEnum.MYBATIS.getValue(), FrameworkEnum.SPRING_CLOUD.getValue()),
    S_SERVICE("/service.ftl","service/%sService.java",
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_IMPL("/impl.ftl","service/impl/%sServiceImpl.java",
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_FORM("/form.ftl","web/form/%sQueryForm.java",
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_CONTROLLER("/controller.ftl","web/controller/%sController.java",
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),

    /************* API *************/
    S_FEIGN("/feign.ftl","feign/I%sClient.java",
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_FALLBACK("/fallback.ftl", "fallback/%sClientFallback.java",
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_DTO("/dto.ftl", "model/%sDTO.java",
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_PARAM("/param.ftl", "model/%sParam.java",
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),
    S_QUERY_PARAM("/query_param.ftl", "model/%sQueryParam.java",
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.SPRING_CLOUD.getValue()),

    ;
    
    /** 模版文件名 */
    private String templatePath;
    /** 文件输出路径 */
    private String outPath;
    /** 模块名 */
    private String module;
    /** 数据层模版类型 为空则是都支持，否则只支持该模版 */
    private String category;
    /** 框架类型 */
    private String framework;

    /**
     * 文件生成后的路径
     * @param className
     * @return
     */
    public String genOutPath(String className) {
        return String.format(getOutPath(), className);
    }

    public String getTemplateName(){
        return name().substring(2);
    }

    /**
     * 获取模版文件的路径
     * @param category 模版类型
     * @return 框架名 + 模版名 + 模版文件名
     */
    public String genTemplatePath(String category) {
        return getFramework().concat("/").concat(category).concat(getTemplatePath());
    }

    public static List<TemplatePathEnum> getTemplates(String framework){
        return Stream.of(TemplatePathEnum.values()).filter(e -> e.getFramework().equalsIgnoreCase(framework)).collect(Collectors.toList());
    }
}
