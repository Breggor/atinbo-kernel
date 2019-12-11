package com.atinbo.swagger.config;

import com.atinbo.swagger.annotation.ApiProperties;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zenghao
 * @date 2019-12-10
 */
@Slf4j
@Order
@Component
public class SwaggerModelReader implements ParameterBuilderPlugin {

    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
        Optional<ApiProperties> optional = methodParameter.findAnnotation(ApiProperties.class);
        if (optional.isPresent()) {
            Class originClass = parameterContext.resolvedMethodParameter().getParameterType().getErasedType();
            //model 名称
            ApiModel apiModel = (ApiModel) originClass.getDeclaredAnnotation(ApiModel.class);
            String name = apiModel == null ? originClass.getSimpleName() + "Model" : apiModel.value();
            String[] properties = null;
            ApiProperties apiProperties = optional.get();
            boolean ignoreType = apiProperties.exclude().length > 0;
            if (ignoreType) {
                properties = apiProperties.exclude();
            } else {
                properties = apiProperties.include();
            }
            try {
                //像documentContext的Models中添加我们新生成的Class
                parameterContext.getDocumentationContext()
                        .getAdditionalModels()
                        .add(typeResolver.resolve(createRefModelIgp(properties, name, originClass, ignoreType)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            parameterContext.parameterBuilder()  //修改model参数的ModelRef为我们动态生成的class
                    .parameterType("body")
                    .modelRef(new ModelRef(name))
                    .name(name);
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    /**
     * 根据 properties 中的值动态生成含有Swagger注解的javaBeen
     */
    private Class createRefModelIgp(String[] properties, String name, Class origin, boolean ignoreType) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(origin.getPackage().getName() + "." + name);
        try {
            Field[] fields = origin.getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            List<String> dealProperties = Arrays.asList(properties);
            List<Field> dealFields = fieldList.stream().filter(
                    s -> ignoreType ? (!(dealProperties.contains(s.getName()))) : dealProperties.contains(s.getName())
            ).collect(Collectors.toList());
            createCtFields(dealFields, ctClass);
            return ctClass.toClass();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createCtFields(List<Field> dealFileds, CtClass ctClass) throws CannotCompileException, NotFoundException {
        for (Field field : dealFileds) {
            CtField ctField = new CtField(ClassPool.getDefault().get(field.getType().getName()), field.getName(), ctClass);
            ctField.setModifiers(Modifier.PUBLIC);
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            String apiModelPropertyValue = java.util.Optional.ofNullable(annotation).map(s -> s.value()).orElse("");
            //添加model属性说明
            if (!StringUtils.isEmpty(apiModelPropertyValue)) {
                ConstPool constPool = ctClass.getClassFile().getConstPool();
                AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                Annotation ann = new Annotation(ApiModelProperty.class.getName(), constPool);
                ann.addMemberValue("value", new StringMemberValue(apiModelPropertyValue, constPool));
                attr.addAnnotation(ann);
                ctField.getFieldInfo().addAttribute(attr);
            }
            ctClass.addField(ctField);
        }
    }

}
