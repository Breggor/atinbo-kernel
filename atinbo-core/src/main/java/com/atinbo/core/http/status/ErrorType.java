package com.atinbo.core.http.status;


/**
 * 错误类型
 *
 * @author breggor
 */
public enum ErrorType {
    FORMAT_INVALID("格式错误"),
    DATA_NOT_FOUND("数据不存在"),
    DATA_EXISTED("数据已存在"),
    DATA_INVALID("数据无效"),
    LOGIN_REQUIRED("没有登录"),
    PERMISSION_DENIED("权限不足");


    private String label;

    ErrorType(String label) {
        this.label = label;
    }
}
