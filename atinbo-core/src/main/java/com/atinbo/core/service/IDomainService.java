package com.atinbo.core.service;


import com.atinbo.core.exception.ServiceApiException;

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
     * @throws ServiceApiException
     */
    T create(T entity) throws ServiceApiException;

    /**
     * 更新
     *
     * @param entity
     * @return
     * @throws ServiceApiException
     */
    T update(T entity) throws ServiceApiException;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws ServiceApiException
     */
    T deleteById(Long id) throws ServiceApiException;
}
