package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"methodeComptable\"")
public class MethodeComptable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idMethodeComptable\"")
    private Long idMethodeComptable;

    @Column(name = "\"libelle\"")
    private String libelle;

    public MethodeComptable() {
    }

    public MethodeComptable(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdMethodeComptable() {
        return idMethodeComptable;
    }

    public void setIdMethodeComptable(Long idMethodeComptable) {
        this.idMethodeComptable = idMethodeComptable;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}