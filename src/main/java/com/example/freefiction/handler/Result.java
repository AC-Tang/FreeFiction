package com.example.freefiction.handler;

import lombok.Data;

@Data
public class Result <T> {
    // 响应业务状态
    private int code;
    // 响应消息
    private String message;
    // 响应中的数据
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T data) {
        this.code = 200;
        this.message = "ok";
        this.data = data;
    }

    public static <T> Result<T> fail(int code, String message){
        return new Result<T>(code,message,null);
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public static <T> Result<T> error(int code ,T data){
        return new Result<T>(code,"--error--",data);
    }

    public static <T> Result<T> AuthFailure(String message){
        return new Result<T>(401,message,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
