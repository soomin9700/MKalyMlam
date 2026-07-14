package com.mkalymlam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "\"modePaiement\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idModePaiement\"")
    private Long idModePaiement;

    @Column(name = "\"libelle\"")
    private String libelle;

    public ModePaiement() {
    }

    public Long getIdModePaiement() {
        return idModePaiement;
    }

    public void setIdModePaiement(Long idModePaiement) {
        this.idModePaiement = idModePaiement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
