package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"typeNotification\"")
public class TypeNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeNotification\"")
    private Long idTypeNotification;

    @Column(name = "\"libelle\"", nullable = false)
    private String libelle;

    public TypeNotification() {
    }

    public Long getIdTypeNotification() {
        return idTypeNotification;
    }

    public void setIdTypeNotification(Long idTypeNotification) {
        this.idTypeNotification = idTypeNotification;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
