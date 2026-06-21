package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "nom_produit")
    private String nomProduit;

    @Column(name = "prix_base")
    private Double prixBase;

    @Column(name = "est_nouveau")
    private Boolean estNouveau;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    public Produit() {
    }

    public Produit(String nomProduit,
                   Double prixBase,
                   Boolean estNouveau,
                   LocalDate dateCreation) {
        this.nomProduit = nomProduit;
        this.prixBase = prixBase;
        this.estNouveau = estNouveau;
        this.dateCreation = dateCreation;
    }

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