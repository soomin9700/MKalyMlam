package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"ligneCommande\"")
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idLigne\"")
    private Long idLine;

    @Column(name = "\"idCommande\"")
    private Long idCommande;

    @Column(name = "\"idProduit\"")
    private Long idProduit;

    @Column(name = "\"quantite\"")
    private Integer quantite;

    @Column(name = "\"prixUnitaireFacture\"")
    private Double prixUnitaireFacture;

    @Column(name = "\"sousTotal\"")
    private Double sousTotal;

    public LigneCommande() {}

    public Long getIdLine() { return idLine; }
    public void setIdLine(Long idLine) { this.idLine = idLine; }

    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }

    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public Double getPrixUnitaireFacture() { return prixUnitaireFacture; }
    public void setPrixUnitaireFacture(Double prixUnitaireFacture) { this.prixUnitaireFacture = prixUnitaireFacture; }

    public Double getSousTotal() { return sousTotal; }
    public void setSousTotal(Double sousTotal) { this.sousTotal = sousTotal; }
}
