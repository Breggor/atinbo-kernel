package com.atinbo.generate.core;

/**
 * 常量类
 * @author code-generator
 * @date 2019-8-20
 */
public abstract class Constant {
    
    
    public static final String CONTROLLER_TEMPLATE_PATH = "controller.ftl";
    public static final String SERVICE_TEMPLATE_PATH = "service.ftl";
    public static final String SERVICE_IMPL_TEMPLATE_PATH = "service_impl.ftl";
    public static final String MAPPER_TEMPLATE_PATH = "mapper.ftl";
    public static final String MODEL_TEMPLATE_PATH = "entity.ftl";
    public static final String MYBATIS_TEMPLATE_PATH = "mybatis.ftl";

    public static final String CONTROLLER_PATH = "controller/%sController.java";
    public static final String SERVICE_PATH = "service/%sService.java";
    public static final String SERVICE_IMPL_PATH = "impl/%sServiceImpl.java";
    public static final String MAPPER_PATH = "mapper/%sMapper.java";
    public static final String MODEL_PATH = "entity/%s.java";
    public static final String MYBATIS_PATH = "mybaits/%sMapper.xml";
}
