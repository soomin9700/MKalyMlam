package com.mkalymlam.recette.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class RecetteBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_produit")
    private Long id_produit;


    @Column(name = "id_ingredient")
    private Long id_ingredient;
    
    @Column(name = "quantite_recette")
    private Double quantite_recette;


    public Long getId() {
        return id;
    }

    public Long getId_produit() {
        return id_produit;
    }

    public Long getId_ingredient() {
        return id_ingredient;
    }

    public Double getQuantite_recette() {
        return quantite_recette;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setId_produit(Long id_produit) {
        this.id_produit = id_produit;
    }
    public void setId_ingredient(Long id_ingredient) {
        this.id_ingredient = id_ingredient;
    }
    public void setQuantite_recette(Double quantite_recette) {
        this.quantite_recette = quantite_recette;
    }

}