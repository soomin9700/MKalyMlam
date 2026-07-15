package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "\"lotIngredient\"")
public class LotIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idLot\"")
    private Long idLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idIngredient\"", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeMouvement\"")
    private TypeMouvement typeMouvement;

    @Column(name = "\"dateReception\"", nullable = false)
    private LocalDate dateReception;

    @Column(name = "\"datePeremption\"", nullable = false)
    private LocalDate datePeremption;

    @Column(name = "\"quantiteInitiale\"", nullable = false)
    private Double quantiteInitiale;

    @Column(name = "\"prixAchatUnitaire\"", nullable = false)
    private Double prixAchatUnitaire;

    @Transient
    private Double quantiteRestante;

    @Transient
    private boolean alerte;

    // Constructeur par défaut
    public LotIngredient() {
    }

    // Constructeur avec tous les paramètres sauf idLot (pour la création)
    public LotIngredient(Ingredient ingredient, TypeMouvement typeMouvement, 
                         LocalDate dateReception, LocalDate datePeremption,
                         Double quantiteInitiale, Double prixAchatUnitaire) {
        this.ingredient = ingredient;
        this.typeMouvement = typeMouvement;
        this.dateReception = dateReception;
        this.datePeremption = datePeremption;
        this.quantiteInitiale = quantiteInitiale;
        this.prixAchatUnitaire = prixAchatUnitaire;
        this.quantiteRestante = quantiteInitiale;
        this.alerte = false;
    }

    // Constructeur avec tous les paramètres
    public LotIngredient(Long idLot, Ingredient ingredient, TypeMouvement typeMouvement,
                         LocalDate dateReception, LocalDate datePeremption,
                         Double quantiteInitiale, Double prixAchatUnitaire,
                         Double quantiteRestante, boolean alerte) {
        this.idLot = idLot;
        this.ingredient = ingredient;
        this.typeMouvement = typeMouvement;
        this.dateReception = dateReception;
        this.datePeremption = datePeremption;
        this.quantiteInitiale = quantiteInitiale;
        this.prixAchatUnitaire = prixAchatUnitaire;
        this.quantiteRestante = quantiteRestante;
        this.alerte = alerte;
    }

    // Getters et Setters
    public Long getIdLot() {
        return idLot;
    }

    public void setIdLot(Long idLot) {
        this.idLot = idLot;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public LocalDate getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    public Double getQuantiteInitiale() {
        return quantiteInitiale;
    }

    public void setQuantiteInitiale(Double quantiteInitiale) {
        this.quantiteInitiale = quantiteInitiale;
    }

    public Double getPrixAchatUnitaire() {
        return prixAchatUnitaire;
    }

    public void setPrixAchatUnitaire(Double prixAchatUnitaire) {
        this.prixAchatUnitaire = prixAchatUnitaire;
    }

    public Double getQuantiteRestante() {
        return quantiteRestante;
    }

    public void setQuantiteRestante(Double quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public boolean isAlerte() {
        return alerte;
    }

    public void setAlerte(boolean alerte) {
        this.alerte = alerte;
    }

    // Méthode utilitaire pour vérifier si le lot est périmé
    public boolean isPerime() {
        if (datePeremption == null) {
            return false;
        }
        return datePeremption.isBefore(LocalDate.now());
    }

    // Méthode utilitaire pour calculer la quantité utilisée
    public Double getQuantiteUtilisee() {
        if (quantiteInitiale == null || quantiteRestante == null) {
            return 0.0;
        }
        return quantiteInitiale - quantiteRestante;
    }

    // Méthode utilitaire pour vérifier si le stock est suffisant
    public boolean hasStockSuffisant(Double quantiteDemandee) {
        if (quantiteRestante == null || quantiteDemandee == null) {
            return false;
        }
        return quantiteRestante >= quantiteDemandee;
    }

    @Override
    public String toString() {
        return "LotIngredient{" +
                "idLot=" + idLot +
                ", ingredient=" + (ingredient != null ? ingredient.getNomIngredient() : null) +
                ", typeMouvement=" + (typeMouvement != null ? typeMouvement.getLibelle() : null) +
                ", dateReception=" + dateReception +
                ", datePeremption=" + datePeremption +
                ", quantiteInitiale=" + quantiteInitiale +
                ", prixAchatUnitaire=" + prixAchatUnitaire +
                ", quantiteRestante=" + quantiteRestante +
                ", alerte=" + alerte +
                '}';
    }
}