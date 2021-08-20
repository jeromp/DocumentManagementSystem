package com.github.jeromp.DocumentManagementSystem.repository;

import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {
    public Meta findByDocument(Document document);
}