package com.github.jeromp.documentmanagementsystem.persistence.bootstrap;

import com.github.jeromp.documentmanagementsystem.persistence.model.DocumentType;
import com.github.jeromp.documentmanagementsystem.persistence.repository.DocumentRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import com.github.jeromp.documentmanagementsystem.persistence.model.Document;
import com.github.jeromp.documentmanagementsystem.persistence.model.Meta;


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
        document.setType(DocumentType.FILE);
        Meta meta = new Meta();
        meta.setDescription("This is a sample description");
        meta.setDocumentCreated(LocalDateTime.now());
        meta.setDocument(document);
        document.setMeta(meta);
        return document;
    }
}
