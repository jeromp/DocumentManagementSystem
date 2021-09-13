package com.github.jeromp.documentmanagementsystem.application.config;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentServicePort;
import com.github.jeromp.documentmanagementsystem.business.service.DocumentService;
import com.github.jeromp.documentmanagementsystem.persistence.DocumentPersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.persistence.config.PersistenceConfig;
import com.github.jeromp.documentmanagementsystem.rest.config.RestApiConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(value = {PersistenceConfig.class, RestApiConfig.class})
public class DocumentConfiguration {

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
