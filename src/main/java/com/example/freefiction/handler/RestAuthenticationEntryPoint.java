package com.example.freefiction.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (authException instanceof BadCredentialsException) {
            // 用户名或密码错误

            Result<?> result = Result.fail(401, "未登录，请重新登录");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        } else if(authException instanceof InsufficientAuthenticationException){
            // 请求头缺失Authorization、Token格式错误、Token过期、签名验证失败

            Result<?> result = Result.fail(401, "登录过期，请重新登录");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        }else{
            // 其他未明确处理的认证异常（如账户被锁定、账户禁用等）
            response.getWriter().write(authException.getMessage());
        }
    }

}
