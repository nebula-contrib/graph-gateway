package com.lpz.graph.gateway.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.exception.BizException;
import com.lpz.graph.gateway.common.param.resp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 非法参数验证异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error("fieldErrors" + JSON.toJSONString(list));
        return Response.buildFailure(ErrorCode.PARAM_ERROR);
    }


    /**
     * HTTP解析请求参数异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("httpMessageNotReadableException:", exception);
        return Response.buildFailure(ErrorCode.PARAM_ERROR);
    }

    /**
     * HTTP
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response httpMediaTypeException(HttpMediaTypeException exception) {
        log.error("httpMediaTypeException:", exception);
        return Response.buildFailure(ErrorCode.MEDIA_TYPE_ERROR);
    }


    /**
     * csrf等无权限403异常处理（CsrfException）
     * InvalidCsrfTokenException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = InvalidCsrfTokenException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response invalidCsrfTokenExceptionHandler(InvalidCsrfTokenException exception) {
        log.error("InvalidCsrfTokenException:", exception);
        return Response.buildFailure(ErrorCode.FORBIDDEN_REQUEST);
    }

    /**
     * csrf等无权限403异常处理（CsrfException）
     * // AccessDeniedException 统一处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response csrfExceptionHandler(AccessDeniedException exception) {
        log.error("AccessDeniedException:", exception);
        return Response.buildFailure(ErrorCode.FORBIDDEN_REQUEST);
    }


    /**
     * 默认的异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Response exceptionHandler(Exception exception) {
        log.error("Exception:" + exception.getMessage(), exception);
        return Response.buildFailure(ErrorCode.INTENAL_ERROR);
    }

    /**
     * 增加业务异常捕获处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response exceptionHandler(BizException exception) {
        log.error("BizException:", exception);
        return Response.buildFailure(exception.getErrCode(), exception.getErrMessage());
    }

}
