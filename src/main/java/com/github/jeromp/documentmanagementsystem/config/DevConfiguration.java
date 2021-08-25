package com.github.jeromp.documentmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.jeromp.documentmanagementsystem.bootstrap.DatabaseBootstrap;

@Configuration
public class DevConfiguration {

    @Bean
    public DatabaseBootstrap databaseBootstrap(){
        return new DatabaseBootstrap();
    }
}