package com.mkalymlam.recette.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "recetteDeBase")
@IdClass(RecetteBaseId.class)
public class RecetteBase {

    @Id
    @Column(name = "idProduit")
    private Long idProduit;

    @Id
    @Column(name = "idIngredient")
    private Long idIngredient;

    @Column(name = "quantiteRecette", nullable = false)
    private Double quantiteRecette;

    public RecetteBase() {
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public Long getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Long idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Double getQuantiteRecette() {
        return quantiteRecette;
    }

    public void setQuantiteRecette(Double quantiteRecette) {
        this.quantiteRecette = quantiteRecette;
    }
}