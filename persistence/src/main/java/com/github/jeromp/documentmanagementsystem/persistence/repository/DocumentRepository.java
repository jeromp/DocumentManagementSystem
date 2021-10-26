package com.github.jeromp.documentmanagementsystem.persistence.repository;

import com.github.jeromp.documentmanagementsystem.persistence.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUuid(UUID uuid);

    @Query("SELECT d, m FROM Document d JOIN d.meta m where " +
            "(:title is null or d.title = :title) AND " +
            "(:description is null or m.description = :description) AND " +
            //"m.documentCreated BETWEEN :documentCreatedAfter AND :documentCreatedBefore")
            "(:documentCreatedAfter is null or (m.documentCreated >= :documentCreatedAfter)) AND " +
            "(:documentCreatedBefore is null or (m.documentCreated <= :documentCreatedBefore))")
    List<Document> findByQuery(@Param("title") Optional<String> title,
                               @Param("description") Optional<String> description,
                               @Param("documentCreatedAfter") Optional<LocalDateTime> documentCreatedAfter,
                               @Param("documentCreatedBefore") Optional<LocalDateTime> documentCreatedBefore);
}