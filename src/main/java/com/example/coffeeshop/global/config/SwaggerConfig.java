package com.example.coffeeshop.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Coffee Shop") // API의 제목
                .description("프로그래머스 BE2 팀10 1번재 프로젝트입니다") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }



}
