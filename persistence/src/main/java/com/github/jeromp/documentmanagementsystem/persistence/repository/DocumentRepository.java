package com.github.jeromp.documentmanagementsystem.persistence.repository;

import com.github.jeromp.documentmanagementsystem.persistence.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUuid(UUID uuid);

    @Query("SELECT d, m FROM Document d JOIN d.meta m where " +
            ":title is null or d.title = :title and " +
            ":description is null or m.description = :description")
    List<Document> findByQuery(@Param("title") String title,
                               @Param("description") String description /*,
                               @Param("documentCreatedAfter") Date documentCreatedAfter,
                               @Param("documentCreatedBefore") Date documentCreatedBefore*/);
}