package com.atinbo.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author breggor
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    private Class<T> modelClass;

    public BaseServiceImpl() {
        Type type = this.getClass().getGenericSuperclass();
        this.modelClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
    }


    @Override
    public boolean deleteLogic(List<Integer> ids) {
        return super.removeByIds(ids);
    }

}
