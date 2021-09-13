package com.github.documentmanagementsystem.rest;

import com.github.jeromp.documentmanagementsystem.rest.config.RestApiConfig;
import com.github.jeromp.documentmanagementsystem.rest.dto.mapper.DocumentDtoMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAutoConfiguration
public class RestApiTestConfig extends RestApiConfig {

}

