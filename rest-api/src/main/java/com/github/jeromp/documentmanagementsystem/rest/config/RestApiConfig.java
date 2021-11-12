package com.github.jeromp.documentmanagementsystem.rest.config;

import com.github.jeromp.documentmanagementsystem.business.adapter.DocumentDataPersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.business.adapter.DocumentFilePersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentDataPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentFilePersistencePort;
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
    public DocumentDataPersistencePort documentDataPersistence() {
        return new DocumentDataPersistenceAdapter();
    }

    @Bean
    @Primary
    public DocumentFilePersistencePort documentFilePersistence() {
        return new DocumentFilePersistenceAdapter();
    }

    @Bean
    public DocumentServicePort documentService() {
        return new DocumentService(documentDataPersistence(), documentFilePersistence());
    }
}
