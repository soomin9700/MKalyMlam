package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produit_avec_disponibilite")
public class ProduitAvecDisponibilite {

    @Id
    @Column(name = "\"idProduit\"")
    private Long idProduit;

    @Column(name = "\"nomProduit\"")
    private String nomProduit;

    @Column(name = "\"prixBase\"")
    private Double prixBase;

    @Column(name = "\"estNouveau\"")
    private Boolean estNouveau;

    @Column(name = "\"dateCreation\"")
    private LocalDate dateCreation;

    @Column(name = "\"estDisponible\"")
    private Boolean estDisponible;

    // Constructeurs
    public ProduitAvecDisponibilite() {}

    public ProduitAvecDisponibilite(Long idProduit, String nomProduit, Double prixBase, 
                                    Boolean estNouveau, LocalDate dateCreation, Boolean estDisponible) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.prixBase = prixBase;
        this.estNouveau = estNouveau;
        this.dateCreation = dateCreation;
        this.estDisponible = estDisponible;
    }

    // Getters et Setters
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

    public Boolean getEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(Boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    @Override
    public String toString() {
        return "ProduitAvecDisponibilite{" +
                "idProduit=" + idProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", prixBase=" + prixBase +
                ", estNouveau=" + estNouveau +
                ", dateCreation=" + dateCreation +
                ", estDisponible=" + estDisponible +
                '}';
    }
}