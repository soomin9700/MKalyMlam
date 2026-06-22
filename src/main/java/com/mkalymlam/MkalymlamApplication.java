package com.mkalymlam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.mkalymlam", "service", "connexion", "controller" })
public class MkalymlamApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkalymlamApplication.class, args);
	}

}
