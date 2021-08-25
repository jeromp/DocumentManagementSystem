package com.github.jeromp.documentmanagementsystem.repository;

import com.github.jeromp.documentmanagementsystem.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUuid(UUID uuid);
}