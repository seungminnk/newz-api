package com.newz.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
	info = @Info(
			title = "Newz api 서버",
			version = "v1",
			description = "Newz api 서버 문서입니다."
	),
	servers = {
		@Server(url = "localhost:3001", description = "로컬 서버"),
		@Server(url = "https://newz.bbear.kr", description = "메인 서버"),
	}
)
@SpringBootApplication
@MapperScan(annotationClass = org.apache.ibatis.annotations.Mapper.class)
public class NewzApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewzApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("*");
			}
		};
	}

}
