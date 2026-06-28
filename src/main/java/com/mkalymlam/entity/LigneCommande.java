package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ligne_commande")
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idline")
    private Long idLine;

    @Column(name = "idcommande")
    private Long idCommande;

    @Column(name = "idproduit")
    private Long idProduit; // Impérativement Long pour correspondre à Produit

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "prixunitairefacture")
    private Double prixUnitaireFacture;

    @Column(name = "soustotal")
    private Double sousTotal;

    public LigneCommande() {}

    // ==========================================
    // GETTERS ET SETTERS SANS ERREUR DE TYPE
    // ==========================================

    public Long getIdLine() {
        return idLine;
    }

    public void setIdLine(Long idLine) {
        this.idLine = idLine;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getIdProduit() { // DOIT retourner un Long
        return idProduit;
    }

    public void setIdProduit(Long idProduit) { // DOIT accepter un Long
        this.idProduit = idProduit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaireFacture() {
        return prixUnitaireFacture;
    }

    public void setPrixUnitaireFacture(Double prixUnitaireFacture) {
        this.prixUnitaireFacture = prixUnitaireFacture;
    }

    public Double getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(Double sousTotal) {
        this.sousTotal = sousTotal;
    }
}