package com.github.jeromp.documentmanagementsystem.business.config;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentServicePort;
import com.github.jeromp.documentmanagementsystem.business.service.DocumentService;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@SpringBootConfiguration
@EnableAutoConfiguration
@Profile("test")
public class BusinessTestConfig {
}

