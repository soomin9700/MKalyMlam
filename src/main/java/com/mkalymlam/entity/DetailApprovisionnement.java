package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"detailApprovisionnement\"")
public class DetailApprovisionnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idDetailApprovisionnement\"")
    private Long idDetailApprovisionnement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idApprovisionnement\"")
    private Approvisionnement approvisionnement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idIngredient\"")
    private Ingredient ingredient;

    @Column(name = "\"stockActuel\"")
    private Double stockActuel;

    @Column(name = "\"prixEstimeUnitaire\"")
    private Double prixEstimeUnitaire;

    @Column(name = "\"quantiteAAcheter\"")
    private Double quantiteAAcheter;

    @Column(name = "\"coutEstime\"")
    private Double coutEstime;

    public DetailApprovisionnement() {
    }

    public Long getIdDetailApprovisionnement() {
        return idDetailApprovisionnement;
    }

    public void setIdDetailApprovisionnement(Long idDetailApprovisionnement) {
        this.idDetailApprovisionnement = idDetailApprovisionnement;
    }

    public Approvisionnement getApprovisionnement() {
        return approvisionnement;
    }

    public void setApprovisionnement(Approvisionnement approvisionnement) {
        this.approvisionnement = approvisionnement;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(Double stockActuel) {
        this.stockActuel = stockActuel;
    }

    public Double getPrixEstimeUnitaire() {
        return prixEstimeUnitaire;
    }

    public void setPrixEstimeUnitaire(Double prixEstimeUnitaire) {
        this.prixEstimeUnitaire = prixEstimeUnitaire;
    }

    public Double getQuantiteAAcheter() {
        return quantiteAAcheter;
    }

    public void setQuantiteAAcheter(Double quantiteAAcheter) {
        this.quantiteAAcheter = quantiteAAcheter;
    }

    public Double getCoutEstime() {
        return coutEstime;
    }

    public void setCoutEstime(Double coutEstime) {
        this.coutEstime = coutEstime;
    }
}
