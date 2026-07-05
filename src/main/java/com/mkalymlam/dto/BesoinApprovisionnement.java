package com.mkalymlam.dto;

public class BesoinApprovisionnement {

    private Long idIngredient;
    private String nomIngredient;
    private String uniteMesure;
    private Double stockActuel;
    private Double seuilStock;
    private Double prixEstimeUnitaire;
    private Double quantiteAAcheter;
    private Double coutEstime;

    public BesoinApprovisionnement(Long idIngredient, String nomIngredient, String uniteMesure,
                                   Double stockActuel, Double seuilStock, Double prixEstimeUnitaire,
                                   Double quantiteAAcheter, Double coutEstime) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.uniteMesure = uniteMesure;
        this.stockActuel = stockActuel;
        this.seuilStock = seuilStock;
        this.prixEstimeUnitaire = prixEstimeUnitaire;
        this.quantiteAAcheter = quantiteAAcheter;
        this.coutEstime = coutEstime;
    }

    public Long getIdIngredient() {
        return idIngredient;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public Double getStockActuel() {
        return stockActuel;
    }

    public Double getSeuilStock() {
        return seuilStock;
    }

    public Double getPrixEstimeUnitaire() {
        return prixEstimeUnitaire;
    }

    public Double getQuantiteAAcheter() {
        return quantiteAAcheter;
    }

    public Double getCoutEstime() {
        return coutEstime;
    }
}
