package com.mkalymlam.dto;

public class IngredientStockDTO {
    private Long idIngredient;
    private String nomIngredient;
    private String uniteMesure;
    private Double seuilAlerte;
    private Double quantiteTotale;
    private Double valeurTotale;
    private String statut; // "DISPONIBLE", "ALERTE", "RUPTURE"

    public IngredientStockDTO() {}

    public IngredientStockDTO(Long idIngredient, String nomIngredient, String uniteMesure,
                              Double seuilAlerte, Double quantiteTotale, Double valeurTotale) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.uniteMesure = uniteMesure;
        this.seuilAlerte = seuilAlerte;
        this.quantiteTotale = quantiteTotale;
        this.valeurTotale = valeurTotale;
        this.statut = determineStatut(quantiteTotale, seuilAlerte);
    }

    private String determineStatut(Double quantite, Double seuil) {
        if (quantite == null || quantite == 0) {
            return "RUPTURE";
        } else if (seuil != null && quantite <= seuil) {
            return "ALERTE";
        } else {
            return "DISPONIBLE";
        }
    }

    // Getters et Setters
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

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public Double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(Double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public Double getQuantiteTotale() {
        return quantiteTotale;
    }

    public void setQuantiteTotale(Double quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
        if (this.quantiteTotale != null) {
            this.statut = determineStatut(this.quantiteTotale, this.seuilAlerte);
        }
    }

    public Double getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(Double valeurTotale) {
        this.valeurTotale = valeurTotale;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}