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
            meta.setKey("description");
            meta.setValue("This is a sample description");
            meta.setDocument(document);
            this.metaRepository.save(meta);
        }
    }
}