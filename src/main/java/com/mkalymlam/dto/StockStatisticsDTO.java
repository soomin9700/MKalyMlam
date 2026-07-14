package com.mkalymlam.dto;

public class StockStatisticsDTO {
    private Long totalIngredients;
    private Long ingredientsDisponibles;
    private Long ingredientsEnRupture;
    private Long ingredientsEnAlerte;
    private Double valeurTotaleStock;

    public StockStatisticsDTO() {}

    public StockStatisticsDTO(Long totalIngredients, Long ingredientsDisponibles, 
                              Long ingredientsEnRupture, Long ingredientsEnAlerte,
                              Double valeurTotaleStock) {
        this.totalIngredients = totalIngredients;
        this.ingredientsDisponibles = ingredientsDisponibles;
        this.ingredientsEnRupture = ingredientsEnRupture;
        this.ingredientsEnAlerte = ingredientsEnAlerte;
        this.valeurTotaleStock = valeurTotaleStock;
    }

    // Getters et Setters
    public Long getTotalIngredients() {
        return totalIngredients;
    }

    public void setTotalIngredients(Long totalIngredients) {
        this.totalIngredients = totalIngredients;
    }

    public Long getIngredientsDisponibles() {
        return ingredientsDisponibles;
    }

    public void setIngredientsDisponibles(Long ingredientsDisponibles) {
        this.ingredientsDisponibles = ingredientsDisponibles;
    }

    public Long getIngredientsEnRupture() {
        return ingredientsEnRupture;
    }

    public void setIngredientsEnRupture(Long ingredientsEnRupture) {
        this.ingredientsEnRupture = ingredientsEnRupture;
    }

    public Long getIngredientsEnAlerte() {
        return ingredientsEnAlerte;
    }

    public void setIngredientsEnAlerte(Long ingredientsEnAlerte) {
        this.ingredientsEnAlerte = ingredientsEnAlerte;
    }

    public Double getValeurTotaleStock() {
        return valeurTotaleStock;
    }

    public void setValeurTotaleStock(Double valeurTotaleStock) {
        this.valeurTotaleStock = valeurTotaleStock;
    }
}