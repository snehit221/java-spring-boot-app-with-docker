package com.example.tutorial5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Tutorial5Application {

	public static void main(String[] args) {
		System.out.println("*****RUNNNING*****");
		SpringApplication.run(Tutorial5Application.class, args);
	}

}
