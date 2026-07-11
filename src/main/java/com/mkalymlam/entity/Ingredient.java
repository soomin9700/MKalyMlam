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
    @Column(name = "\"idIngredient\"")
    private Long idIngredient;

    @Column(name = "\"nomIngredient\"")
    private String nomIngredient;

    @Column(name = "\"seuilAlerteQuantite\"")
    private Double seuilAlerteQuantite;

    @Column(name = "\"uniteMesure\"")
    private String uniteMesure;
    
    @Column(name = "\"actif\"")
    private Boolean actif;

    public Ingredient(Long idIngredient, String nomIngredient, Double seuilAlerteQuantite, String uniteMesure,
            Boolean actif) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.seuilAlerteQuantite = seuilAlerteQuantite;
        this.uniteMesure = uniteMesure;
        this.actif = actif;
    }

    public Ingredient() {
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

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }


    
}