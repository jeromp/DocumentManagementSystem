package com.github.jeromp.documentmanagementsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(RevListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected Long id;

    @Column(name = "created")
    protected LocalDateTime created;

    @Column(name = "changed")
    protected LocalDateTime changed;

    public Long getId() {
        return this.id;
    }

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
