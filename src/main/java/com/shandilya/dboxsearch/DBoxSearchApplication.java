package com.shandilya.dboxsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DBoxSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBoxSearchApplication.class, args);
	}

}
