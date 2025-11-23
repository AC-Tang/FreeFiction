package com.example.freefiction.controller;

import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/api")
public class CaptchaController {
    @Autowired
    private Producer kaptchaProducer;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
        HttpSession session=request.getSession();
        session.setAttribute("captcha", text);
        response.setContentType("image/jpeg");
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}
