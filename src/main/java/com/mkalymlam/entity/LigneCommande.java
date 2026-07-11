package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "\"ligneCommande\"")
public class LigneCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idLigne\"")
    private Long idLigneCommande;
    
    @Column(name = "\"idCommande\"")
    private Long idCommande;
    
    @Column(name = "\"idProduit\"")
    private Long idProduit;
    
    @Column(name = "quantite")
    private Integer quantite;  // ✅ Changé int → Integer
    
    @Column(name = "\"prixUnitaireFacture\"")
    private Double prixUnitaireFacture;  // ✅ Ajouté
    
    @Transient
    private Double sousTotal;

    // Constructeurs
    public LigneCommande() {}

    public LigneCommande(Long idCommande, Long idProduit, Integer quantite, Double prixUnitaireFacture) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prixUnitaireFacture = prixUnitaireFacture;
        this.sousTotal = quantite * prixUnitaireFacture;
    }

    // Getters et Setters
    public Long getIdLigneCommande() { return idLigneCommande; }
    public void setIdLigneCommande(Long idLigneCommande) { this.idLigneCommande = idLigneCommande; }

    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }

    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public Double getPrixUnitaireFacture() { return prixUnitaireFacture; }
    public void setPrixUnitaireFacture(Double prixUnitaireFacture) { this.prixUnitaireFacture = prixUnitaireFacture; }

    public Double getSousTotal() { 
        if (sousTotal == null && prixUnitaireFacture != null && quantite != null) {
            sousTotal = prixUnitaireFacture * quantite;
        }
        return sousTotal; 
    }
    
    public void setSousTotal(Double sousTotal) { this.sousTotal = sousTotal; }
}