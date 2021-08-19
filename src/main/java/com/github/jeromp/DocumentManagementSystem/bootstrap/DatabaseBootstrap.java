package com.github.jeromp.DocumentManagementSystem.bootstrap;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.repository.MetaRepository;

public class DatabaseBootstrap implements InitializingBean {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MetaRepository metaRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        setUpDocumentDatabase();
    }

    private void setUpDocumentDatabase() {
        List<Document> documentList = this.documentRepository.findAll();
        Document document;
        if(documentList.size() < 1) {
            document = new Document();
            document.setTitle("Sample Document");
            document.setPath("sample_path");
            document.setUuid(UUID.randomUUID());
            document = this.documentRepository.save(document);
        } else {
            document = documentList.get(0);
        }
        setUpMetaDatabase(document);
    }

    private void setUpMetaDatabase(Document document) {
        List<Meta> metaList = this.metaRepository.findAll();
        if(metaList.size() < 1) {
            Meta meta = new Meta();
            meta.setDescription("This is a sample description");
            meta.setDocumentCreated(LocalDateTime.now());
            meta.setDocument(document);
            this.metaRepository.save(meta);
        }
    }
}