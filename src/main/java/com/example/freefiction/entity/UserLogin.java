package com.example.freefiction.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogin{
    private Integer Id;
    private String userName;
    private String passwordHash;
    private String email;
    private String salt;
    private String nickName;
    private String avatar;
    private String gender;
    private Date birthday;
    private String signature;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Date lastLoginTime;
}
