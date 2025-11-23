package com.example.freefiction.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public Producer kaptchaProducer() {
        Properties props = new Properties();
        // 图像尺寸 200*50
        props.setProperty("kaptcha.image.width", "200");
        props.setProperty("kaptcha.image.height", "50");
        props.setProperty("kaptcha.textproducer.char.length", "4");
        props.setProperty("kaptcha.textproducer.font.names", "Arial,宋体");
        props.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHJKLMNPQRSTUVWXYZ23456789");
        props.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(new Config(props));
        return kaptcha;
    }
}
