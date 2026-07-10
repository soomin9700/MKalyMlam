package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "typeCommande")
public class TypeCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTypeCommande")
    private Long idTypeCommande;
    
    @Column(name = "libelle")
    private String libelle;
    
    public TypeCommande() {
    }
    
    public TypeCommande(String libelle) {
        this.libelle = libelle;
    }
    
    public Long getIdTypeCommande() {
        return idTypeCommande;
    }
    
    public void setIdTypeCommande(Long idTypeCommande) {
        this.idTypeCommande = idTypeCommande;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}