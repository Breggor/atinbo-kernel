package com.atinbo.webmvc.handler;

import com.atinbo.core.http.model.ErrResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.atinbo.core.constants.HttpStatusCode.ERR_500;


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
        return ErrResult.error(ERR_500.getHttpCode(), ERR_500.getMessage(), ex.getMessage());
    }
}