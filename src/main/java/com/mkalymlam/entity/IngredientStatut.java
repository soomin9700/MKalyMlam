package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"ingredientStatut\"")
public class IngredientStatut {

    @Id
    @Column(name = "\"idIngredient\"")
    private Long idIngredient;

    @OneToOne
    @MapsId
    @JoinColumn(name = "\"idIngredient\"")
    private Ingredient ingredient;

    @Column(name = "\"statutActif\"")
    private Boolean statutActif = true;

    public IngredientStatut() {
    }

    public IngredientStatut(Ingredient ingredient, Boolean statutActif) {
        this.ingredient = ingredient;
        this.statutActif = statutActif;
    }

    public Long getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Long idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Boolean getStatutActif() {
        return statutActif;
    }

    public void setStatutActif(Boolean statutActif) {
        this.statutActif = statutActif;
    }
}
