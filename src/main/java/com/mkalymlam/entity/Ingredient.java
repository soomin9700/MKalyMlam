package com.mkalymlam.entity;

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
    @Column(name = "idingredient")
    private Long idIngredient;

    @Column(name = "nomingredient")
    private String nomIngredient;
    @Column(name = "seuilalertequantite")
    private Double seuilAlerteQuantite;
    @Column(name = "unitemesure")
    private String uniteMesure;


    public Ingredient() {
    }

    public Ingredient(String nomIngredient, Double seuilAlerteQuantite, String uniteMesure) {
        this.nomIngredient = nomIngredient;
        this.seuilAlerteQuantite = seuilAlerteQuantite;
        this.uniteMesure = uniteMesure;
    }


    public Long getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Long idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public Double getSeuilAlerteQuantite() {
        return seuilAlerteQuantite;
    }

    public void setSeuilAlerteQuantite(Double seuilAlerteQuantite) {
        this.seuilAlerteQuantite = seuilAlerteQuantite;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }
}