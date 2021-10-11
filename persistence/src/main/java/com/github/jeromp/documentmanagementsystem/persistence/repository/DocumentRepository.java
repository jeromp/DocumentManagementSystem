package com.github.jeromp.documentmanagementsystem.persistence.repository;

import com.github.jeromp.documentmanagementsystem.persistence.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUuid(UUID uuid);
}