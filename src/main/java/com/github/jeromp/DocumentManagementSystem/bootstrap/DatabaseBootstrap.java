package com.github.jeromp.DocumentManagementSystem.bootstrap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;

public class DatabaseBootstrap implements InitializingBean {
    @Autowired
    DocumentRepository repository;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Document> documentList = repository.findAll();
        if(documentList.size() < 1) {
            Document document = new Document();
            document.setTitle("Sample Document");
            document.setPath("sample_path");
            repository.save(document);
        }
    }
}