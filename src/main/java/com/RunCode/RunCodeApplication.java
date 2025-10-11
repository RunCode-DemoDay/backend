package com.RunCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RunCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunCodeApplication.class, args);
	}

}
