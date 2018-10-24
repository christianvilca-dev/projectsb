package com.christian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectsbApplication.class, args);
	}
}
