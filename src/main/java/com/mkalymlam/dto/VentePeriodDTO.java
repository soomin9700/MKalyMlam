package com.mkalymlam.dto;

public class VentePeriodDTO {
    private String periode; // "2024-01", "2024-01-15", etc.
    private Long nombreVentes;
    private Double chiffreAffaires;
    private Long quantiteVendue;

    public VentePeriodDTO() {}

    public VentePeriodDTO(String periode, Long nombreVentes, Double chiffreAffaires, Long quantiteVendue) {
        this.periode = periode;
        this.nombreVentes = nombreVentes;
        this.chiffreAffaires = chiffreAffaires;
        this.quantiteVendue = quantiteVendue;
    }

    // Getters et Setters
    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public Long getNombreVentes() { return nombreVentes; }
    public void setNombreVentes(Long nombreVentes) { this.nombreVentes = nombreVentes; }

    public Double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(Double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }

    public Long getQuantiteVendue() { return quantiteVendue; }
    public void setQuantiteVendue(Long quantiteVendue) { this.quantiteVendue = quantiteVendue; }
}