package com.github.rogeryk.charity.server.web.controller.exceptionhandle;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.web.utils.Response;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class CommonExceptionHandle {

    //使用@RequestBody修饰参数时，参数验证失败会抛出此异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response argumentExceptionHandle(MethodArgumentNotValidException e) {
        return Response.error(ErrorCodes.PARAMS_ERROR, e.getMessage());
    }

    //url 上的参数验证失败会抛出此异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response violationExceptionHandle(ConstraintViolationException e) {
        return Response.error(ErrorCodes.PARAMS_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public Response conflictExceptionHandle(ObjectOptimisticLockingFailureException e) {
        return Response.error(ErrorCodes.OPTIMISTIC_CONFLICT, "服务器繁忙，请稍后重试");
    }

    //TODO 解决ErrorCodes.UNLOGIN 状态码为UNAUTHORIZED;
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceException.class)
    public Response serviceExceptionHandle(ServiceException e) {
        return Response.error(e.getCode(), e.getMsg());
    }
}
