package com.github.jeromp.documentmanagementsystem.persistence.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class RevListener {
    @PrePersist
    private void prePersist(BaseEntity entity){
        entity.setCreated(LocalDateTime.now());
        entity.setChanged(LocalDateTime.now());
    }

    @PreUpdate
    private void preUpdate(BaseEntity entity){
        entity.setChanged(LocalDateTime.now());
    }
}
