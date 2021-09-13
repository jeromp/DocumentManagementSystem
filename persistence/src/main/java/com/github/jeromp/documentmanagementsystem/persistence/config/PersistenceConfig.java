package com.github.jeromp.documentmanagementsystem.persistence.config;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.persistence.DocumentPersistenceAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.github.jeromp.documentmanagementsystem.persistence"})
@EntityScan(basePackages = {"com.github.jeromp.documentmanagementsystem.persistence"})
@ComponentScan(basePackages = {"com.github.jeromp.documentmanagementsystem.persistence"})
public class PersistenceConfig {

    @Bean
    public DocumentPersistencePort documentPersistence() {
        return new DocumentPersistenceAdapter();
    }
}
