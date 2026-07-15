package com.mkalymlam.dto;

public class VenteStatsDTO {
    private Long nombreVentes;
    private Double chiffreAffaires;
    private Double moyennePanier;
    private Long nombreProduitsVendus;

    public VenteStatsDTO() {}

    public VenteStatsDTO(Long nombreVentes, Double chiffreAffaires, Double moyennePanier, Long nombreProduitsVendus) {
        this.nombreVentes = nombreVentes;
        this.chiffreAffaires = chiffreAffaires;
        this.moyennePanier = moyennePanier;
        this.nombreProduitsVendus = nombreProduitsVendus;
    }

    // Getters et Setters
    public Long getNombreVentes() { return nombreVentes; }
    public void setNombreVentes(Long nombreVentes) { this.nombreVentes = nombreVentes; }

    public Double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(Double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }

    public Double getMoyennePanier() { return moyennePanier; }
    public void setMoyennePanier(Double moyennePanier) { this.moyennePanier = moyennePanier; }

    public Long getNombreProduitsVendus() { return nombreProduitsVendus; }
    public void setNombreProduitsVendus(Long nombreProduitsVendus) { this.nombreProduitsVendus = nombreProduitsVendus; }
}