package com.github.jeromp.DocumentManagementSystem;

import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DocumentApiRestControllerTest {

    @Autowired
    private DocumentRepository documentRepository;
    private DocumentStorageService documentStorageService;

    @Test
    void getDocumentById() {

    }

    @Test
    void postDocument(){

    }
}
