package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"ingredient\"")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idIngredient\"")
    private Integer idIngredient;

    @Column(name = "\"nomIngredient\"")
    private String nomIngredient;

    @Column(name = "\"seuilAlerteQuantite\"")
    private Double seuilAlerteQuantite;

    @Column(name = "\"uniteMesure\"")
    private String uniteMesure;

    // AJOUT DE LA COLONNE PRIX
    @Column(name = "prix")
    private Double prix;

    public Ingredient() {}

    // Getters et Setters existants
    public Integer getIdIngredient() { return idIngredient; }
    public void setIdIngredient(Integer id) { this.idIngredient = id; }

    public String getNomIngredient() { return nomIngredient; }
    public void setNomIngredient(String nomIngredient) { this.nomIngredient = nomIngredient; }

    public Double getSeuilAlerteQuantite() { return seuilAlerteQuantite; }
    public void setSeuilAlerteQuantite(Double seuilAlerteQuantite) { this.seuilAlerteQuantite = seuilAlerteQuantite; }

    public String getUniteMesure() { return uniteMesure; }
    public void setUniteMesure(String uniteMesure) { this.uniteMesure = uniteMesure; }

    // NOUVEAUX GETTERS ET SETTERS POUR LE PRIX
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
}
