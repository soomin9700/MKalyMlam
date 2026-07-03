package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"typeMouvement\"")
public class TypeMouvement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeMouvement\"")
    private Long id;

    @Column(name = "\"libelle\"")
    private String libelle;

    public TypeMouvement(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public TypeMouvement() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}