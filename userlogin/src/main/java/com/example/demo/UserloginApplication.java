package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.example")
@EnableJpaRepositories("com.example.repository")
@EntityScan("com.example.entity")
public class UserloginApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserloginApplication.class, args);
	}

}
