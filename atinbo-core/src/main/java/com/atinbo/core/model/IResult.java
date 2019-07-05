package com.atinbo.core.model;

/**
 * 返回数据接口
 * Created by Breggor on 2017/04/21.
 */
public interface IResult {

    /**
     * 请求状态码
     */
    String getCode();

    /**
     * 前台提示信息
     */
    String getMsg();

    /**
     * 后台错误信息
     */
    String getErrorMsg();

    /**
     * 请求成功后，业务数据
     *
     * @return
     */
    Object getData();

    /**
     * 请求失败返回数据
     *
     * @return
     */
    Object getErrorData();
}