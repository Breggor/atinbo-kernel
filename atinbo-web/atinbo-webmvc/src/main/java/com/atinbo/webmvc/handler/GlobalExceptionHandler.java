package com.atinbo.webmvc.handler;

import com.atinbo.core.model.ErrResult;
import com.atinbo.model.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author breggor
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 全局异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public ErrResult handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ErrResult.error(StatusCodeEnum.INTERNAL_SERVER_ERROR.getCode(), StatusCodeEnum.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
    }
}