package com.github.jeromp.documentmanagementsystem.business.config;

import com.github.jeromp.documentmanagementsystem.business.adapter.DocumentPersistenceAdapter;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@ComponentScan(basePackages = {"com.github.jeromp.documentmanagementsystem.business"})
public class PersistenceAdapterConfig {

    @Bean
    @Primary
    public DocumentPersistencePort documentPersistence() {
        return new DocumentPersistenceAdapter();
    }
}
