package com.atinbo.mybatis;

import com.atinbo.common.reflections.ReflectionUtils;
import com.atinbo.model.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author zenghao
 * @date 2019-11-06
 */
public class DynamicCondition {

    public static <T> Wrapper<T> toWrapper(final QueryParam queryParam){
        QueryWrapper wrapper = Wrappers.query();
        List<Field> fieldList = ReflectionUtils.getDeclaredFields(queryParam.getClass(), PageParam.class);

        String fieldName;
        Object fieldValue;
        Operator operator;
        SortDirection sort;
        Query queryMode;
        SortInfo sortInfo = new SortInfo();
        for (Field field : fieldList) {
            fieldValue = ReflectionUtils.invokeGetterMethod(queryParam, field.getName());
            if (fieldValue == null || (field.getType() == String.class && StringUtils.isEmpty((String) fieldValue))) {
                continue;
            }

            if (field.isAnnotationPresent(Query.class)) {
                queryMode = field.getAnnotation(Query.class);
                if (queryMode.ignore()) {
                    continue;
                }
                fieldName = StringUtils.isEmpty(queryMode.field()) ? field.getName() : queryMode.field();
                operator = queryMode.operator();
                sort = queryMode.order();
            } else {
                fieldName = field.getName();
                operator = Operator.EQ;
                sort = SortDirection.NULL;
            }
            fieldName = StringUtils.camelToUnderline(fieldName);

            /*************************拼接查询条件**************************/
            switch (operator) {
                case EQ:
                case ENUMEQ:
                    wrapper.eq(fieldName, fieldValue);
                    break;
                case LIKE:
                    wrapper.like(fieldName, fieldValue);
                    break;
                case GT:
                    wrapper.gt(fieldName, fieldValue);
                    break;
                case LT:
                    wrapper.lt(fieldName, fieldValue);
                    break;
                case GTE:
                    wrapper.ge(fieldName, fieldValue);
                    break;
                case LTE:
                    wrapper.le(fieldName, fieldValue);
                    break;
                case NOTNULL:
                    wrapper.isNotNull(fieldName);
                    break;
                case ISNULL:
                    wrapper.isNull(fieldName);
                    break;
                case IN:
                    wrapper.in(fieldName, fieldValue);
                    break;
                default:
                    break;
            }
            /*************************拼接排序条件**************************/
            if(SortDirection.NULL != sort){
                sortInfo.addField(sort,fieldName);
            }
        }
        if(!sortInfo.isEmpty()) {
            wrapper.orderByDesc(sortInfo.get(SortDirection.DESC).toArray());
            wrapper.orderByAsc(sortInfo.get(SortDirection.ASC).toArray());
        }

        return wrapper;
    }
}
