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
    @Column(name = "\"idMouvementLot\"")
    private Long idMouvementLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idLot\"")
    private LotIngredient lot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeMouvement\"")
    private TypeMouvement typeMouvement;

    @Column(name = "\"quantite\"")
    private Double quantite;

    @Column(name = "\"dateMouvement\"")
    private LocalDateTime dateMouvement;

    public MouvementLotIngredient() {
    }

    public MouvementLotIngredient(LotIngredient lot, TypeMouvement typeMouvement, Double quantite,
            LocalDateTime dateMouvement) {
        this.lot = lot;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
        this.dateMouvement = dateMouvement;
    }

    public Long getIdMouvementLot() {
        return idMouvementLot;
    }

    public void setIdMouvementLot(Long idMouvementLot) {
        this.idMouvementLot = idMouvementLot;
    }

    public LotIngredient getLot() {
        return lot;
    }

    public void setLot(LotIngredient lot) {
        this.lot = lot;
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
