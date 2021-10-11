package com.github.jeromp.documentmanagementsystem.rest;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.rest.config.RestApiConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
@EnableAutoConfiguration
public class RestApiTestConfig extends RestApiConfig {

    @MockBean
    public DocumentPersistencePort documentPersistencePort;

}

