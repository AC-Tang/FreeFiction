package com.example.freefiction.config;

import com.example.freefiction.filter.JwtAuthenticationTokenFilter;
import com.example.freefiction.handler.RestAuthenticationEntryPoint;
import com.example.freefiction.handler.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {//自定义Security策略
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {

            "/test/**",
            "/",
            "/error",
            "/*.html",
            "/*.png",
            "/*.css",
            "/*.js",
            "/favicon.ico",
            "/swagger-resources/**",
            "/webjars/**",
            "/doc.html",
            "/public/**",
            "/static/**",
            "/public/**",
            "/druid/**",
            "/swagger-ui/**",
            "/v3/**",
            "/api/captcha",
            "/api/auth/login",
            "/api/register",
            "/api/logout",

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 启用CORS并配置源
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                // 禁用CSRF
                .csrf(csrf->csrf.disable())
                // 基于Token,不需要session
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 禁用 X-Frame-Options 响应头，允许页面被嵌套到 iframe 中
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //开启异常处理
                .exceptionHandling(exception->exception
                        //处理认证异常
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        //处理授权异常
                        .accessDeniedHandler(restfulAccessDeniedHandler))
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    /*
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }
    */

    // Cors配置源
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config=new CorsConfiguration();

        //Collections.singletonList("*");
        // 允许的源
        config.setAllowedOriginPatterns(Arrays.asList(
                "*"
        ));

        // 允许的方法
        config.setAllowedMethods(Arrays.asList(
                "GET","POST","PUT","PATCH","DELETE","OPTIONS"
        ));

        // 允许的请求头
        config.setAllowedHeaders(Arrays.asList(
                "Authorization","Content-Type","X-Requested-With",
                "Accept","X-CSRF-Token"
        ));

        // 暴露的响应头
        config.setExposedHeaders(Arrays.asList(
                "Content-Dispostion","X-Content-Disposition"
        ));

        // 允许携带凭证(Cookies等)
        config.setAllowCredentials(true);

        // 预检请求缓存时间(30分钟)
        config.setMaxAge(1800L);

        // 为所有路径应用此配置
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);

        return source;
    }

    /**
     * 创建并配置密码编码器Bean
     * 在Spring Security中，提供了若干种密码解析器实现类型，其中最常用的是BCryptPasswordEncoder。
     * 该方法用于创建BCryptPasswordEncoder实例，用于密码的加密和验证操作。
     * 密码加密和解析器
     * BCrypt是一种安全的密码哈希算法，具有salt随机化和自适应成本控制特性。
     * 强散列加密：可以保证相同的明文，多次加密后，密码有相同的散列数据,而不是相同的结果。
     * 匹配时，是基于相同的散列数据做的匹配。
     *
     * @return PasswordEncoder 返回配置好的BCrypt密码编码器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
