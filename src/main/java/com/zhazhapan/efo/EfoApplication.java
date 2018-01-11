package com.zhazhapan.efo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pantao
 */
@SpringBootApplication
@MapperScan
public class EfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfoApplication.class, args);
	}
}
