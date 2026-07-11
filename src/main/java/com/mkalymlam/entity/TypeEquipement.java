package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"typeEquipement\"")
public class TypeEquipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeEquipement\"")
    private Long idTypeEquipement;

    @Column(name = "\"libelle\"")
    private String libelle;

    public TypeEquipement() {
    }

    public TypeEquipement(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdTypeEquipement() {
        return idTypeEquipement;
    }

    public void setIdTypeEquipement(Long idTypeEquipement) {
        this.idTypeEquipement = idTypeEquipement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}