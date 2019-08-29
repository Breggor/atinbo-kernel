package com.atinbo.core.query;


import com.atinbo.model.Operator;
import com.atinbo.model.PageParam;
import com.atinbo.model.Query;
import com.atinbo.model.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用方法： 为（数据库）实体类创建一个查询对象类，该类需要实现 QueryParam 接口
 *           因部分查询对象都继承了 PageParam ，故此处拼接忽略了 PageParam 中的属性
 *             查询对象类的属性和实体类属性一致，并且是全匹配查询时不需要做任何处理
 *             否则需要在查询属性加上 @Query 注解，注解使用方法请查看注解类中的注释
 *
 * JPA 动态查询条件拼接
 *
 * @author zenghao
 * @create 2019/7/23
 */
public class DynamicSpecifications {

    public static <T> Specification<T> toSpecification(final QueryParam queryParam) {
        return (Specification<T>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Field> fieldList = ReflectionUtils.getDeclaredFields(queryParam.getClass(), PageParam.class);

            String fieldName;
            Object fieldValue;
            Operator operator;
            Query queryMode;
            for (Field field : fieldList) {
                fieldValue = ReflectionUtils.invokeGetterMethod(queryParam, field.getName());
                if (fieldValue == null || (field.getType() == String.class && StringUtils.isBlank((String)fieldValue))) {
                    continue;
                }

                if (field.isAnnotationPresent(Query.class)) {
                    queryMode = field.getAnnotation(Query.class);
                    if (queryMode.ignore()) {
                        continue;
                    }
                    fieldName = StringUtils.isBlank(queryMode.field()) ? field.getName() : queryMode.field();
                    operator = queryMode.operator();
                } else {
                    fieldName = field.getName();
                    operator = Operator.EQ;
                }
                switch (operator) {
                    case EQ:
                    case ENUMEQ:
                        predicates.add(builder.equal(root.get(fieldName), fieldValue));
                        break;
                    case LIKE:
                        predicates.add(builder.like(root.get(fieldName), "%" + fieldValue + "%"));
                        break;
                    case GT:
                        predicates.add(builder.greaterThan(root.get(fieldName), (Comparable) fieldValue));
                        break;
                    case LT:
                        predicates.add(builder.lessThan(root.get(fieldName), (Comparable) fieldValue));
                        break;
                    case GTE:
                        predicates.add(builder.greaterThanOrEqualTo(root.get(fieldName), (Comparable) fieldValue));
                        break;
                    case LTE:
                        predicates.add(builder.lessThanOrEqualTo(root.get(fieldName), (Comparable) fieldValue));
                        break;
                    case NOTNULL:
                        predicates.add(builder.isNotNull(root.get(fieldName)));
                        break;
                    case ISNULL:
                        predicates.add(builder.isNull(root.get(fieldName)));
                        break;
                    default:
                        break;
                }
            }

            // 将所有条件用 and 联合起来
            if (!predicates.isEmpty()) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            return builder.conjunction();
        };
    }

}
