package com.atinbo.support.core.framework.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class DataSourceAspect {

    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null) {
                if (m.isAnnotationPresent(DataSourceAnnotation.DataSource.class)) {
                    DataSourceAnnotation.DataSource data = m.getAnnotation(DataSourceAnnotation.DataSource.class);
                    MultipleDataSource.setDataSourceKey(data.value());
                } else {
                    MultipleDataSource.clearDataSourceKey();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
