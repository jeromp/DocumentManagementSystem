package com.github.jeromp.documentmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class DocumentManagementSystem {

	public static void main(String[] args) {
		SpringApplication.run(DocumentManagementSystem.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World";
	}

}
