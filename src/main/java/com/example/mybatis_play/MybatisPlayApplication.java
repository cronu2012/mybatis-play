package com.example.mybatis_play;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mybatis_play.daomapper")
public class MybatisPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlayApplication.class, args);
	}

}
