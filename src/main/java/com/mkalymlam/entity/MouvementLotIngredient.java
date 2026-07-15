package com.mkalymlam.entity;

import java.time.LocalDateTime;

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
@Table(name = "\"mouvementLotIngredient\"")
public class MouvementLotIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idmouvementLot\"")
    private Long idmouvementLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeMouvement\"")
    private TypeMouvement typeMouvement;


    @Column(name = "\"quantite\"")
    private Double quantite;

    @Column(name = "\"dateMouvement\"")
    private LocalDate dateMouvement;

    public MouvementLotIngredient(Long idmouvementLot, TypeMouvement typeMouvement,
            LotIngredient lotIngredient, Double quantite, LocalDate dateMouvement) {
        this.idmouvementLot = idmouvementLot;
        this.typeMouvement = typeMouvement;
        this.lotIngredient = lotIngredient;
        this.quantite = quantite;
        this.dateMouvement = dateMouvement;
    }

    public MouvementLotIngredient() {
    }

    public Long getidmouvementLot() {
        return idmouvementLot;
    }

    public void setidmouvementLot(Long idmouvementLot) {
        this.idmouvementLot = idmouvementLot;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
}
