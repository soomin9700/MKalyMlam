package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"actionCommande\"")
public class ActionCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idActionCommande\"")
    private Long idActionCommande;

    @Column(name = "\"libelle\"")
    private String libelle;

    public ActionCommande() {
    }

    public ActionCommande(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdActionCommande() {
        return idActionCommande;
    }

    public void setIdActionCommande(Long idActionCommande) {
        this.idActionCommande = idActionCommande;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
