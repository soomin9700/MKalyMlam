package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"typeItem\"")
public class TypeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idInventaire\"")
    private Long idInventaire;

    @Column(name = "\"libelle\"")
    private String libelle;

    public TypeItem(Long idInventaire, String libelle) {
        this.idInventaire = idInventaire;
        this.libelle = libelle;
    }

    public TypeItem() {
    }

    public Long getIdInventaire() {
        return idInventaire;
    }

    public void setIdInventaire(Long idInventaire) {
        this.idInventaire = idInventaire;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}
