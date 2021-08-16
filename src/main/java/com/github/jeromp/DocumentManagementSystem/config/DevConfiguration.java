package com.github.jeromp.DocumentManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.jeromp.DocumentManagementSystem.bootstrap.DatabaseBootstrap;

@Configuration
public class DevConfiguration {

    @Bean
    public DatabaseBootstrap databaseBootstrap(){
        return new DatabaseBootstrap();
    }
}