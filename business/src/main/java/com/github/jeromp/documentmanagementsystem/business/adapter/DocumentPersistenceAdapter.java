package com.github.jeromp.documentmanagementsystem.business.adapter;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.service.DocumentServiceException;
import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.persistence.mapper.DocumentMapper;
import com.github.jeromp.documentmanagementsystem.persistence.repository.DocumentRepository;
import com.github.jeromp.documentmanagementsystem.persistence.storage.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentPersistenceAdapter implements DocumentPersistencePort {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentStorageService documentStorageService;

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public DocumentBo findByUuid(UUID uuid) {
        var document = this.documentRepository.findByUuid(uuid).orElseThrow(() -> new DocumentServiceException(HttpStatus.NOT_FOUND, "Document with id: " + uuid + " not found."));
        return this.documentMapper.documentToDocumentBo(document);
    }

    @Override
    public void create(InputStream file, String fileName) {
        this.documentStorageService.create(file, fileName);
    }

    @Override
    public DocumentBo save(DocumentBo documentBo) {
        var document = this.documentRepository.save(this.documentMapper.documentBoToDocument(documentBo));
        return this.documentMapper.documentToDocumentBo(document);
    }

    @Override
    public List<DocumentBo> findAll() {
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
