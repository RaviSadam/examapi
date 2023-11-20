package com.ravi.examapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExamapiApplication {
	public static void main(String[] args){
		SpringApplication.run(ExamapiApplication.class, args);
	}
}
