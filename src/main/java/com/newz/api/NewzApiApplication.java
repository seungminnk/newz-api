package com.newz.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(annotationClass = org.apache.ibatis.annotations.Mapper.class)
public class NewzApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewzApiApplication.class, args);
	}

}
