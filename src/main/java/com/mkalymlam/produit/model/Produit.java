package com.mkalymlam.produit.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduit")
    private Long id;

    @Column(name = "nomProduit", nullable = false)
    private String nomProduit;

    @Column(name = "prixBase", nullable = false)
    private double prixBase;

    @Column(name = "estNouveau")
    private Boolean estNouveau;

    @Column(name = "dateCreation")
    private LocalDate dateCreation;

    public Produit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
    }

    public Boolean getEstNouveau() {
        return estNouveau;
    }

    public void setEstNouveau(Boolean estNouveau) {
        this.estNouveau = estNouveau;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
}