package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idProduit\"")  // ← Guillemets doubles !
    private Long idProduit;

    @Column(name = "\"nomProduit\"")
    private String nomProduit;

    @Column(name = "\"prixBase\"")
    private Double prixBase;

    @Column(name = "\"estNouveau\"")
    private Boolean estNouveau;

    @Column(name = "\"dateCreation\"")
    private LocalDate dateCreation;

    public Produit() {}

    public Produit(String nomProduit, Double prixBase, Boolean estNouveau, LocalDate dateCreation) {
        this.nomProduit = nomProduit;
        this.prixBase = prixBase;
        this.estNouveau = estNouveau;
        this.dateCreation = dateCreation;
    }

    // Getters et Setters...
    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public Double getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(Double prixBase) {
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