package com.mkalymlam.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"mouvementStock\"")
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idMouvement\"")
    private Long idMouvement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idIngredient\"", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idLot\"", nullable = false)
    private LotIngredient lot;

    @Column(name = "\"typeMouvement\"", nullable = false, length = 20)
    private String typeMouvement; // 'ENTREE', 'SORTIE', 'AJUSTEMENT'

    @Column(name = "\"quantite\"", nullable = false)
    private Double quantite;

    @Column(name = "\"quantiteAvant\"", nullable = false)
    private Double quantiteAvant;

    @Column(name = "\"quantiteApres\"", nullable = false)
    private Double quantiteApres;

    @Column(name = "\"dateMouvement\"")
    private LocalDateTime dateMouvement;

    @Column(name = "\"motif\"", length = 255)
    private String motif;

    @Column(name = "\"idUtilisateur\"")
    private Long idUtilisateur;

    // Constructeurs
    public MouvementStock() {
        this.dateMouvement = LocalDateTime.now();
    }

    public MouvementStock(Ingredient ingredient, LotIngredient lot, String typeMouvement,
                          Double quantite, Double quantiteAvant, Double quantiteApres,
                          String motif, Long idUtilisateur) {
        this.ingredient = ingredient;
        this.lot = lot;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
        this.quantiteAvant = quantiteAvant;
        this.quantiteApres = quantiteApres;
        this.motif = motif;
        this.idUtilisateur = idUtilisateur;
        this.dateMouvement = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getIdMouvement() {
        return idMouvement;
    }

    public void setIdMouvement(Long idMouvement) {
        this.idMouvement = idMouvement;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public LotIngredient getLot() {
        return lot;
    }

    public void setLot(LotIngredient lot) {
        this.lot = lot;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getQuantiteAvant() {
        return quantiteAvant;
    }

    public void setQuantiteAvant(Double quantiteAvant) {
        this.quantiteAvant = quantiteAvant;
    }

    public Double getQuantiteApres() {
        return quantiteApres;
    }

    public void setQuantiteApres(Double quantiteApres) {
        this.quantiteApres = quantiteApres;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}