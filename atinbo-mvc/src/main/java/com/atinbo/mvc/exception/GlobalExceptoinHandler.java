package com.atinbo.mvc.exception;

import com.atinbo.core.exception.APIException;
import com.atinbo.core.http.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.atinbo.core.http.status.impl.Enum500Error.SYSTEM_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptoinHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result bindException(MethodArgumentNotValidException e) {
        return doErr(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException e) {
        return doErr(e.getBindingResult());
    }

    private Result doErr(BindingResult bindingResult2) {
        BindingResult bindingResult = bindingResult2;
        Map<String, String> errs = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errs.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.error(errs);
    }


    @ExceptionHandler(APIException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleBizRuntimeException(HttpServletRequest request,
                                            HttpServletResponse response, APIException ex) {
        return new Result(SYSTEM_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleRuntimeException(HttpServletRequest request,
                                         HttpServletResponse response, RuntimeException ex) {
        return new Result(SYSTEM_ERROR);
    }
}
