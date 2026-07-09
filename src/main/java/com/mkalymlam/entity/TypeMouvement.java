package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"typeMouvement\"")
public class TypeMouvement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeMouvement\"")
    private Long idTypeMouvement;

    @Column(name = "\"libelle\"")
    private String libelle;

    public TypeMouvement() {}

    public TypeMouvement(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdTypeMouvement() {
        return idTypeMouvement;
    }

    public void setIdTypeMouvement(Long idTypeMouvement) {
        this.idTypeMouvement = idTypeMouvement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
