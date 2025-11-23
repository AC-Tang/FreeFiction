package com.example.freefiction.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生异常未找到.", requestURI, e);
        return Result.error(404,e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result  handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',未找到.", requestURI, e);
        return Result.error(404,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e,HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return Result.error(500,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return Result.error(500,e.getMessage());
    }
}
