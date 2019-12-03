package com.paramount.web;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication()
@EnableScheduling
@EnableAsync
@MapperScan(value = "com.paramount.mapper")
@ComponentScan(basePackages = "com.paramount")
public class FilmApplication {

	public static void main( String[] args )
	{
		SpringApplication.run(FilmApplication.class, args);
	}
}
