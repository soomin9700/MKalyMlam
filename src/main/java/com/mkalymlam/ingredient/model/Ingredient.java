package com.mkalymlam.ingredient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idIngredient\"")
    private Long id;

    @Column(name = "\"nomIngredient\"", nullable = false)
    private String nomIngredient;

    @Column(name = "\"seuilAlerteQuantite\"", nullable = false)
    private double seuilAlerteQuantite;

    @Column(name = "\"uniteMesure\"", nullable = false)
    private String uniteMesure;

    public Ingredient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public double getSeuilAlerteQuantite() {
        return seuilAlerteQuantite;
    }

    public void setSeuilAlerteQuantite(double seuilAlerteQuantite) {
        this.seuilAlerteQuantite = seuilAlerteQuantite;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }
}