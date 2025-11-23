package com.example.freefiction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.freefiction.entity.UserLogin;
import com.example.freefiction.mapper.UserLoginMapper;
import com.example.freefiction.service.UserLoginService;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin>
        implements UserLoginService {

}
