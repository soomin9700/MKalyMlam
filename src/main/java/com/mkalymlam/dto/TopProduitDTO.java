package com.mkalymlam.dto;

public class TopProduitDTO {
    private Long idProduit;
    private String nomProduit;
    private Long quantiteVendue;
    private Double chiffreAffaires;
    private Double prixMoyen;

    public TopProduitDTO() {}

    public TopProduitDTO(Long idProduit, String nomProduit, Long quantiteVendue, Double chiffreAffaires, Double prixMoyen) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.quantiteVendue = quantiteVendue;
        this.chiffreAffaires = chiffreAffaires;
        this.prixMoyen = prixMoyen;
    }

    // Getters et Setters
    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }

    public String getNomProduit() { return nomProduit; }
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }

    public Long getQuantiteVendue() { return quantiteVendue; }
    public void setQuantiteVendue(Long quantiteVendue) { this.quantiteVendue = quantiteVendue; }

    public Double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(Double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }

    public Double getPrixMoyen() { return prixMoyen; }
    public void setPrixMoyen(Double prixMoyen) { this.prixMoyen = prixMoyen; }
}