package com.github.jeromp.documentmanagementsystem.rest.config;

import com.github.jeromp.documentmanagementsystem.business.adapter.DocumentPersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentServicePort;
import com.github.jeromp.documentmanagementsystem.business.service.DocumentService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EntityScan(basePackages = {"com.github.jeromp.documentmanagementsystem.rest"})
@ComponentScan(basePackages = {"com.github.jeromp.documentmanagementsystem.rest"})
public class RestApiConfig {

    @Bean
    @Primary
    public DocumentPersistencePort documentPersistence() {
        return new DocumentPersistenceAdapter();
    }

    @Bean
    public DocumentServicePort documentService() {
        return new DocumentService(documentPersistence());
    }
}
