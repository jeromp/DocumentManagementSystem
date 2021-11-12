package com.github.jeromp.documentmanagementsystem.business.config;

import com.github.jeromp.documentmanagementsystem.business.adapter.DocumentDataPersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.business.adapter.DocumentFilePersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentDataPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentFilePersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@ComponentScan(basePackages = {"com.github.jeromp.documentmanagementsystem.business"})
public class PersistenceAdapterConfig {

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
}
