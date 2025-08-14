package com.simon.curso.springboot.jpa.springboot_jpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Audit {

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    
    @PrePersist
    public void prePersist() {
        System.out.println("Evento del ciclo de vida del entity pre-persist");
        this.createAt = LocalDateTime.now();
    }
    
    @PostPersist
    public void postPersist() {
        System.out.println("Evento del ciclo de vida del entity post-persist");
    }
    
    @PreUpdate
    public void preUpdate() {
        System.out.println("Evento del ciclo de vida del entity pre-update");
        this.updateAt = LocalDateTime.now();
    }
    
    @PostUpdate
    public void postUpdate() {
        System.out.println("Evento del ciclo de vida del entity post-update");
    }
    
    public LocalDateTime getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
    
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
    
}
