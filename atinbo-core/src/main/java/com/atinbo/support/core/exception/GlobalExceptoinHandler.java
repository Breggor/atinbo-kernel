package com.atinbo.support.core.exception;

import com.atinbo.support.core.model.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptoinHandler {

    private static final String SERVER_ERROR = "system internal error";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultVO handleBindException(HttpServletRequest request, BindException ex) {
        logger.info("BindException occured in url: " + request.getRequestURI(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        String errorMesssage = "";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage();
            break;
        }
        return ResultVO.msg(errorMesssage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVO handleMethodArgumentNotValidException(HttpServletRequest request,
                                                          MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException occured in url: " + request.getRequestURI(), ex);

        BindingResult bindingResult = ex.getBindingResult();
        String errorMesssage = "";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage();
            break;
        }
        return ResultVO.msg(errorMesssage);
    }

    @ExceptionHandler(BizRuntimeException.class)
    @ResponseBody
    public ResultVO handleBizRuntimeException(HttpServletRequest request,
                                              HttpServletResponse response, BizRuntimeException ex) {
        logger.info("BizRuntimeException({}) occured in url {}", ex.getMessage(), request.getRequestURI());
        return ResultVO.msg(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultVO handleRuntimeException(HttpServletRequest request,
                                           HttpServletResponse response, RuntimeException ex) {
        logger.error("RuntimeException occured in url: " + request.getRequestURI(), ex);
        String errorMesssage = ex.getMessage() == null ? SERVER_ERROR : ex.getMessage();
        return ResultVO.errorMsg(errorMesssage);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(HttpServletRequest request,
                                    HttpServletResponse response, Exception ex) {
        logger.error("Exception occured in url: " + request.getRequestURI(), ex);
        String errorMesssage = ex.getMessage() == null ? SERVER_ERROR : ex.getMessage();
        return ResultVO.errorMsg(errorMesssage);
    }
}
