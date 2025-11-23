package com.example.freefiction.common;

import lombok.Data;

/**
 * 通用返回结果
 * @param <T>
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    /**
     * 成功返回结果
     * @param data 返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     * @param message 返回消息
     * @param data 返回数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed() {
        return new Result<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }

    /**
     * 失败返回结果
     * @param message 返回消息
     */
    public static <T> Result<T> failed(String message) {
        return new Result<>(ResultCode.FAILED.getCode(), message);
    }

    /**
     * 失败返回结果
     * @param code 状态码
     * @param message 返回消息
     */
    public static <T> Result<T> failed(Integer code, String message) {
        return new Result<>(code, message);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     * @param message 返回消息
     */
    public static <T> Result<T> validateFailed(String message) {
        return failed(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized() {
        return failed(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage());
    }

    /**
     * 未登录返回结果
     * @param message 返回消息
     */
    public static <T> Result<T> unauthorized(String message) {
        return failed(ResultCode.UNAUTHORIZED.getCode(), message);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden() {
        return failed(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage());
    }

    /**
     * 未授权返回结果
     * @param message 返回消息
     */
    public static <T> Result<T> forbidden(String message) {
        return failed(ResultCode.FORBIDDEN.getCode(), message);
    }
}