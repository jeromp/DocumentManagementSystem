package com.github.jeromp.documentmanagementsystem.bootstrap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.github.jeromp.documentmanagementsystem.model.Document;
import com.github.jeromp.documentmanagementsystem.model.Meta;
import com.github.jeromp.documentmanagementsystem.repository.DocumentRepository;

public class DatabaseBootstrap implements InitializingBean {
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void afterPropertiesSet() {
        setUpDocumentDatabase();
    }

    private void setUpDocumentDatabase() {
        var document = this.documentRepository.findAll().stream().findFirst().orElseGet(() -> createNewDocument());
        documentRepository.save(document);
    }

    private Document createNewDocument() {
        Document document = new Document();
        document.setTitle("Sample Document");
        document.setPath("sample_path");
        document.setUuid(UUID.randomUUID());
        Meta meta = new Meta();
        meta.setDescription("This is a sample description");
        meta.setDocumentCreated(LocalDateTime.now());
        meta.setDocument(document);
        document.setMeta(meta);
        return document;
    }
}