package com.atinbo.swagger.annotation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zenghao
 * @date 2019-11-07
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({@ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 201, message = "请求成功并且服务器创建了新的资源"),
        @ApiResponse(code = 401, message = "没有权限"),
        @ApiResponse(code = 403, message = "服务器拒绝请求"),
        @ApiResponse(code = 404, message = "未找到"),
        @ApiResponse(code = 500, message = "系统错误")})
public @interface HttpApiResponse {

}
