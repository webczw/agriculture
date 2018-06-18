package com.hjd.power.agriculture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.hjd.power.agriculture.dao")
public class AgricultureApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgricultureApplication.class, args);
	}
}
