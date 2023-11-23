package com.assignments.ecomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages ="com.assignments.ecomerce.*")
@EnableJpaRepositories("com.assignments.ecomerce.repository")
@EntityScan(value = "com.assignments.ecomerce.model")
public class PhoneStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhoneStoreApplication.class, args);
	}

}
