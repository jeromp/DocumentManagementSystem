package com.github.jeromp.documentmanagementsystem.persistence.repository;

import com.github.jeromp.documentmanagementsystem.persistence.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUuid(UUID uuid);

    @Query("SELECT d, m FROM Document d JOIN d.meta m where " +
            "(:title is null or LOWER(d.title) = LOWER(CAST(:title AS text))) AND " +
            "(:description is null or LOWER(m.description) LIKE CONCAT('%', LOWER(CAST(:description AS text)), '%')) AND " +
            "(CAST(:documentCreatedAfter AS timestamp) is null or m.documentCreated >= :documentCreatedAfter) AND " +
            "(CAST(:documentCreatedBefore AS timestamp) is null or :documentCreatedBefore >= m.documentCreated)")
    List<Document> findByQuery(@Param("title") String title,
                               @Param("description") String description,
                               @Param("documentCreatedAfter") LocalDateTime documentCreatedAfter,
                               @Param("documentCreatedBefore") LocalDateTime documentCreatedBefore);
}