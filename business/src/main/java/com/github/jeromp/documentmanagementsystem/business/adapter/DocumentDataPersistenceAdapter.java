package com.github.jeromp.documentmanagementsystem.business.adapter;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentDataPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.service.common.DocumentNotFoundException;
import com.github.jeromp.documentmanagementsystem.business.service.common.DocumentServiceException;
import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.persistence.mapper.DocumentMapper;
import com.github.jeromp.documentmanagementsystem.persistence.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentDataPersistenceAdapter implements DocumentDataPersistencePort {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public DocumentBo readByUuid(UUID uuid) {
        var document = this.documentRepository.findByUuid(uuid).orElseThrow(() -> new DocumentNotFoundException("Document with id: " + uuid + " not found."));
        return this.documentMapper.documentToDocumentBo(document);
    }


    @Override
    public DocumentBo create(DocumentBo documentBo) {
        var document = this.documentRepository.save(this.documentMapper.documentBoToDocument(documentBo));
        return this.documentMapper.documentToDocumentBo(document);
    }

    @Override
    public List<DocumentBo> readAll() {
        var documents = this.documentRepository.findAll();
        return this.documentMapper.mapDocumentsToDocumentBoList(documents);
    }

    @Override
    public List<DocumentBo> findByQuery(String title, String description, LocalDateTime documentCreatedAfter, LocalDateTime documentCreatedBefore) {
        var documents = this.documentRepository.findByQuery(title, description, documentCreatedAfter, documentCreatedBefore);
        return this.documentMapper.mapDocumentsToDocumentBoList(documents);
    }

    @Override
    public void delete(DocumentBo documentBo) {
        var document = this.documentMapper.documentBoToDocument(documentBo);
        this.documentRepository.delete(document);
    }

}
