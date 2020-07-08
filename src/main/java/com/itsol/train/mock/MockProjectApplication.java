package com.itsol.train.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MockProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockProjectApplication.class, args);
	}

}
