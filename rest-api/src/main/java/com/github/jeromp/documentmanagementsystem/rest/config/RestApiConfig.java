package com.github.jeromp.documentmanagementsystem.rest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.github.jeromp.documentmanagementsystem.rest"})
@ComponentScan(basePackages = {"com.github.jeromp.documentmanagementsystem.rest"})
public class RestApiConfig {
}
