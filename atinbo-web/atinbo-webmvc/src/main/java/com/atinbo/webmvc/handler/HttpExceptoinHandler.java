package com.atinbo.webmvc.handler;

import com.atinbo.core.exception.HttpApiException;
import com.atinbo.core.http.model.ErrResult;
import com.atinbo.core.http.status.ErrorType;
import com.atinbo.core.http.status.HttpStatusCode;
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

import java.util.HashMap;
import java.util.Map;

import static com.atinbo.core.http.status.HttpStatusCode.*;


/**
 * SpringMVC 统一拦截
 *
 * @author breggor
 */
@Slf4j
@RestControllerAdvice
public class HttpExceptoinHandler {

    /**
     * 请求参数无法验证异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrResult bindException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return doErr(HttpStatusCode.ERR_400, e.getBindingResult());
    }


    /**
     * 请求没有数据异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return ErrResult.error(HttpStatusCode.ERR_400.getHttpCode(), ErrorType.DATA_INVALID.getLabel());
    }


    /**
     * 请求方法不被服务支持异常拦截
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ErrResult.error(ERR_405.getHttpCode(), ERR_405.getMessage());
    }

    /**
     * 服务器拒绝服务，原因是请求格式不被支持异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ErrResult.error(ERR_415.getHttpCode(), ERR_415.getMessage());
    }

    /**
     * 自定义HttpAPI异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrResult handleHttpAPIException(HttpApiException e) {
        log.error(e.getMessage(), e);
        return ErrResult.error(ERR_500.getHttpCode(), ERR_500.getMessage(), e.getMessage());
    }

    /**
     * 系统内部运行期异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrResult.error(ERR_500.getHttpCode(), ERR_500.getMessage(), e.getMessage());
    }


    private ErrResult doErr(HttpStatusCode code, BindingResult br) {
        Map<String, String> errs = new HashMap<>();
        for (FieldError fieldError : br.getFieldErrors()) {
            errs.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ErrResult.error(code, errs);
    }
}
