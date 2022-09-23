package com.ll.exam.RecipiaProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RecipiaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipiaProjectApplication.class, args);
	}

}
