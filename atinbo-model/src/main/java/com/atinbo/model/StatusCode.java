package com.atinbo.model;

import java.io.Serializable;

/**
 * 状态码接口
 *
 * @author breggor
 */
public interface StatusCode extends Serializable {
    long serialVersionUID = 1L;

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();
}