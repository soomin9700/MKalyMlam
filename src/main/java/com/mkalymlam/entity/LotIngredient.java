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

    @Column(name = "\"dateReception\"", nullable = false)
    private LocalDate dateReception;

    @Column(name = "\"datePeremption\"", nullable = false)
    private LocalDate datePeremption;

    @Column(name = "\"quantiteInitiale\"", nullable = false)
    private Double quantiteInitiale;

    @jakarta.persistence.Transient
    private TypeMouvement typeMouvement;

    @Column(name = "\"prixAchatUnitaire\"", nullable = false)
    private Double prixAchatUnitaire;

    @jakarta.persistence.Transient
    private Double quantiteRestante;

    @jakarta.persistence.Transient
    private boolean alerte;

    public LotIngredient() {
    }

    public LotIngredient(Ingredient ingredient, LocalDate dateReception, LocalDate datePeremption,
            Double quantiteInitiale, Double prixAchatUnitaire) {
        this.ingredient = ingredient;
        this.dateReception = dateReception;
        this.datePeremption = datePeremption;
        this.quantiteInitiale = quantiteInitiale;
        this.prixAchatUnitaire = prixAchatUnitaire;
    }

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

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
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
}