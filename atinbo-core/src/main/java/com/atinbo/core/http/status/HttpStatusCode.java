package com.atinbo.core.http.status;


import java.util.StringJoiner;

/**
 * http status码
 *
 * @author breggor
 */
public enum HttpStatusCode {
    ERR_400(400, "[Bad Request] - 请求参数不合法"),
    ERR_401(401, "[Unauthorized] - 当前请求未通过授权"),
    ERR_403(403, "[Forbidden] - 服务器拒绝执行该请求"),
    ERR_404(404, "[Not Found] -  请求失败:请求所希望得到的资源未被在服务器上发现"),
    ERR_405(405, "[Method Not Allowed] -  请求方法不被允许"),
    ERR_415(415, "[Unsupported Media Type] -  服务器拒绝服务，原因是请求格式不被支持"),
    ERR_500(500, "[Internal Server Error] - 服务器内部错误） 服务器遇到错误，无法完成请求."),
    ERR_503(503, "[Overload] - 由于临时的服务器维护或者过载, 服务器当前无法处理请求.");

    private int httpCode;
    private String message;

    HttpStatusCode(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HttpStatusCode.class.getSimpleName() + "[", "]")
                .add("httpCode=" + httpCode)
                .add("message='" + message + "'")
                .toString();
    }
}
