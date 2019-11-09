package com.atinbo.mybatis.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 基础业务接口
 *
 * @param <T>
 * @author breggor
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 逻辑删除
     *
     * @param ids id集合(逗号分隔)
     * @return
     */
    boolean deleteLogic(List<Integer> ids);

}
