package com.example.fusalmatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FusalMatchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FusalMatchingApplication.class, args);
	}

}
