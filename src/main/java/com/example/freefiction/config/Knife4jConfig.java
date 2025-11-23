package com.example.freefiction.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    //Springboot3集成Knife4j时需要 JDK版本 >= 17

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
        .info(new Info()
                .title("Knife4j整合(OpenAPI 3规范)Swagger3 Api接口文档")
                .description("Knife4j后端接口服务...")
                .version("1.0.0")
                .contact(new Contact().name("Hello").email("HelloWorld@XXX.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
        )
        .externalDocs(new ExternalDocumentation()
            .description("接口设计文档")
            .url("http://localhost:8080/swagger-ui/swagger-ui/index.html"));

    }
}
