package com.atinbo.core.service;


import com.atinbo.core.exception.ServiceAPIException;

import java.util.Optional;


/**
 * 领域服务接口
 *
 * @param <T>
 */
public interface IDomainService<T> {
    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    Optional<T> findById(Long id);

    /**
     * 创建
     *
     * @param entity
     * @return
     * @throws ServiceAPIException
     */
    T create(T entity) throws ServiceAPIException;

    /**
     * 更新
     *
     * @param entity
     * @return
     * @throws ServiceAPIException
     */
    T update(T entity) throws ServiceAPIException;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws ServiceAPIException
     */
    T deleteById(Long id) throws ServiceAPIException;
}
