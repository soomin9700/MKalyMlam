package com.mkalymlam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "\"statutCommande\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StatutCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idStatutCommande\"")
    private Long idStatutCommande;
    
    @Column(name = "\"libelle\"")
    private String libelle;
    public StatutCommande() {
    }
    
    public StatutCommande(String libelle) {
        this.libelle = libelle;
    }
    
    public Long getIdStatutCommande() {
        return idStatutCommande;
    }
    
    public void setIdStatutCommande(Long idStatutCommande) {
        this.idStatutCommande = idStatutCommande;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}