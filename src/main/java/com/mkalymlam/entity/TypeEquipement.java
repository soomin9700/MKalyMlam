package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"typeEquipement\"")
public class TypeEquipement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idTypeEquipement\"")
    private Integer idTypeEquipement;
    
    @Column(name = "\"libelle\"")
    private String libelle;
    
    public TypeEquipement() {
    }
    
    public TypeEquipement(Integer idTypeEquipement, String libelle) {
        this.idTypeEquipement = idTypeEquipement;
        this.libelle = libelle;
    }
    
    public Integer getIdTypeEquipement() {
        return idTypeEquipement;
    }
    
    public void setIdTypeEquipement(Integer idTypeEquipement) {
        this.idTypeEquipement = idTypeEquipement;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}