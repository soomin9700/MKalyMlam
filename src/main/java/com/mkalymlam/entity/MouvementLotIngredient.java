package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idLot\"")
    private LotIngredient lotIngredient;

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

    public LotIngredient getLotIngredient() {
        return lotIngredient;
    }

    public void setLotIngredient(LotIngredient lotIngredient) {
        this.lotIngredient = lotIngredient;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDate dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
}
