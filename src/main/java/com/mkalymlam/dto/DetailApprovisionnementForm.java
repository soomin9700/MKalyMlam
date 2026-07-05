package com.mkalymlam.dto;

public class DetailApprovisionnementForm {

    private Long idIngredient;
    private Double stockActuel;
    private Double prixEstimeUnitaire;
    private Double quantiteAAcheter;
    private Double coutEstime;

    public Long getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Long idIngredient) {
        this.idIngredient = idIngredient;
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
