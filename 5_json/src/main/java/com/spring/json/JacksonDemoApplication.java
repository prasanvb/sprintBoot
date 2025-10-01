package com.spring.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.spring.json")
public class JacksonDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JacksonDemoApplication.class, args);
	}

}
