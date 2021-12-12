package com.qzlnode.epidemic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.qzlnode.epidemic.miniprogram.dao.mysql")
@SpringBootApplication
public class EpidemicApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpidemicApplication.class, args);
	}

}
