package com.jeekim.server.jphappapi.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class JeekimSwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        String key = "Access Token (Bearer)";
        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
        Components components = new Components()
                .addSecuritySchemes(key, accessTokenSecurityScheme);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(key))
                .components(components)
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("지킴 내가 먹는 약 API")
                .description("지킴 내가 먹는 약 API 목록")
                .version("1.0.0");
    }
}
