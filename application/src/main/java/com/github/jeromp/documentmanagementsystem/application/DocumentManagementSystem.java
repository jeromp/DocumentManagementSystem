package com.github.jeromp.documentmanagementsystem.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.jeromp.documentmanagementsystem.persistence", "com.github.jeromp.documentmanagementsystem.rest"})
@EnableAutoConfiguration
public class DocumentManagementSystem {

	public static void main(String[] args) {
		SpringApplication.run(DocumentManagementSystem.class, args);
	}
}
