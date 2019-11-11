package com.atinbo.generate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.io.File;

/**
 * @author zenghao
 * @date 2019-09-03
 */
@Getter
@AllArgsConstructor
public enum TemplatePathEnum {
    
    /*********************************************** DUBBO *************************************************/
    /************* API *************/
    BO("/bo.ftl" , "model/%sBO.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    PARAM("/param.ftl" , "model/%sParam.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    QUERY_PARAM("/query_param.ftl" , "model/%sQueryParam.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    SERVICE("/service.ftl" , "service/%sService.java" ,
            GenerateUtil.MODULE_API , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),

    /************* SERVICE *************/
    ENTITY("/entity.ftl" , "entity/%s.java" ,
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    MAPPER("/mapper.ftl" , "mapper/%sMapper.java" ,
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    DAO("/dao.ftl" , "dao/%sDao.java" ,
            GenerateUtil.MODULE_SERVICE , CategoryEnum.MYBATIS.getValue(), FrameworkEnum.DUBBO.getValue()),
    MYBATIS("/mybatis.ftl" , "mybatis/%sDao.xml" ,
            GenerateUtil.MODULE_SERVICE , CategoryEnum.MYBATIS.getValue(), FrameworkEnum.DUBBO.getValue()),
    REPOSITORY("/repository.ftl" , "repository/%sRepository.java" ,
            GenerateUtil.MODULE_SERVICE , CategoryEnum.HIBERNATE.getValue(), FrameworkEnum.DUBBO.getValue()),
    IMPL("/impl.ftl" , "impl/%sServiceImpl.java" ,
            GenerateUtil.MODULE_SERVICE , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),

    /************* OPENAPI *************/
    VO("/vo.ftl" , "openapi/model/%sVO.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    FORM("/form.ftl" , "openapi/model/%sForm.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    QUERY_FORM("/query_form.ftl" , "openapi/model/%sQueryForm.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    OPENAPI_MAPPER("/openapi_mapper.ftl" , "openapi/mapper/%sMapper.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),
    CONTROLLER("/controller.ftl" , "openapi/controller/%sController.java" ,
            GenerateUtil.MODULE_OPENAPI , Strings.EMPTY, FrameworkEnum.DUBBO.getValue()),

    /*********************************************** SPRING_CLOUD *************************************************/
    
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

    /**
     * 获取模版文件的路径
     * @param category 模版类型
     * @return 框架名 + 模版名 + 模版文件名
     */
    public String genTemplatePath(String framework, String category) {
        return framework.concat(File.separator).concat(category).concat(getTemplatePath());
    }
}
