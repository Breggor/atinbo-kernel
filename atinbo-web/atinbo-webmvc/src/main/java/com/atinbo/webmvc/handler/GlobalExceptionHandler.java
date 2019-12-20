package com.atinbo.webmvc.handler;

import com.atinbo.core.exception.HttpApiException;
import com.atinbo.core.exception.RequestParamException;
import com.atinbo.model.ErrorInfo;
import com.atinbo.model.Outcome;
import com.atinbo.model.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author breggor
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求参数无法验证异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Outcome bindException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return doErr(StatusCodeEnum.PARAM_VALID_ERROR, e.getBindingResult());
    }


    /**
     * 请求没有数据异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Outcome handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Outcome.failure();
    }


    /**
     * 请参数异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RequestParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Outcome requestParamException(RequestParamException e) {
        log.error(e.getMessage(), e);
        return Outcome.failure(e.getMessage());
    }

    /**
     * 请求方法不被服务支持异常拦截
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Outcome handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Outcome.failure(StatusCodeEnum.METHOD_NOT_SUPPORTED);
    }

    /**
     * 服务器拒绝服务，原因是请求格式不被支持异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Outcome handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Outcome.failure(StatusCodeEnum.MEDIA_TYPE_NOT_SUPPORTED);
    }

    /**
     * 自定义HttpAPI异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Outcome handleHttpAPIException(HttpApiException ex) {
        log.error(ex.getMessage(), ex);
        return Outcome.failure(ex.getStatus(), ex.getMessage());
    }

    private Outcome<List<ErrorInfo>> doErr(StatusCodeEnum code, BindingResult br) {
        List<ErrorInfo> result = new ArrayList<>();
        for (FieldError err : br.getFieldErrors()) {
            result.add(ErrorInfo.of(err.getField(), err.getField() + ": " + err.getDefaultMessage()));
        }
        return Outcome.failure(code, result);
    }

    /**
     * 全局异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Outcome handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Outcome.failure(StatusCodeEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}