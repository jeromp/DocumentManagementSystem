package com.github.jeromp.DocumentManagementSystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(RevListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name="created")
    protected LocalDateTime created;

    @Column(name="changed")
    protected LocalDateTime changed;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }
}
