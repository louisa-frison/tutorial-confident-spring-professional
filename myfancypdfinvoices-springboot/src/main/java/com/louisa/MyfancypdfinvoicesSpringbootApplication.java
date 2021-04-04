package com.louisa;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyfancypdfinvoicesSpringbootApplication {

	
	@Bean
	public InitializingBean runner(DataSource dataSource) {
		return () -> {
			System.out.println("This is the datasource this Spring Boot project is using: " + dataSource);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MyfancypdfinvoicesSpringbootApplication.class, args);
	}

}
