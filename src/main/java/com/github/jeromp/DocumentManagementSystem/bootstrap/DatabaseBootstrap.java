package com.github.jeromp.DocumentManagementSystem.bootstrap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.repository.MetaRepository;

public class DatabaseBootstrap implements InitializingBean {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    MetaRepository metaRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        setUpDocumentDatabase();
        setUpMetaDatabase();
    }

    private void setUpDocumentDatabase() {
        List<Document> documentList = documentRepository.findAll();
        if(documentList.size() < 1) {
            Document document = new Document();
            document.setTitle("Sample Document");
            document.setPath("sample_path");
            documentRepository.save(document);
        }
    }

    private void setUpMetaDatabase() {
        List<Meta> metaList = metaRepository.findAll();
        if(metaList.size() < 1) {
            Meta meta = new Meta();
            meta.setKey("description");
            meta.setValue("This is a sample description");
            metaRepository.save(meta);
        }
    }
}