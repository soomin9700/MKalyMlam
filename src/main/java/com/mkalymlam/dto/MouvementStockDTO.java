package com.mkalymlam.dto;

import java.time.LocalDateTime;

public class MouvementStockDTO {
    private Long idMouvement;
    private String nomIngredient;
    private String nomLot;
    private String typeMouvement;
    private Double quantite;
    private Double quantiteAvant;
    private Double quantiteApres;
    private LocalDateTime dateMouvement;
    private String motif;
    private String uniteMesure;

    public MouvementStockDTO() {}

    public MouvementStockDTO(Long idMouvement, String nomIngredient, String nomLot,
                             String typeMouvement, Double quantite, Double quantiteAvant,
                             Double quantiteApres, LocalDateTime dateMouvement,
                             String motif, String uniteMesure) {
        this.idMouvement = idMouvement;
        this.nomIngredient = nomIngredient;
        this.nomLot = nomLot;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
        this.quantiteAvant = quantiteAvant;
        this.quantiteApres = quantiteApres;
        this.dateMouvement = dateMouvement;
        this.motif = motif;
        this.uniteMesure = uniteMesure;
    }

    // Getters et Setters
    public Long getIdMouvement() {
        return idMouvement;
    }

    public void setIdMouvement(Long idMouvement) {
        this.idMouvement = idMouvement;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public String getNomLot() {
        return nomLot;
    }

    public void setNomLot(String nomLot) {
        this.nomLot = nomLot;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getQuantiteAvant() {
        return quantiteAvant;
    }

    public void setQuantiteAvant(Double quantiteAvant) {
        this.quantiteAvant = quantiteAvant;
    }

    public Double getQuantiteApres() {
        return quantiteApres;
    }

    public void setQuantiteApres(Double quantiteApres) {
        this.quantiteApres = quantiteApres;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }
}