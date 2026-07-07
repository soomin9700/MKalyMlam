package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "\"mouvementLotIngredient\"")
public class MouvementLotIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idMouvementLotIngredient\"")
    private Long idMouvementLotIngredient;

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

    public MouvementLotIngredient(Long idMouvementLotIngredient, TypeMouvement typeMouvement,
            LotIngredient lotIngredient, Double quantite, LocalDate dateMouvement) {
        this.idMouvementLotIngredient = idMouvementLotIngredient;
        this.typeMouvement = typeMouvement;
        this.lotIngredient = lotIngredient;
        this.quantite = quantite;
        this.dateMouvement = dateMouvement;
    }

    public MouvementLotIngredient() {
    }

    public Long getIdMouvementLotIngredient() {
        return idMouvementLotIngredient;
    }

    public void setIdMouvementLotIngredient(Long idMouvementLotIngredient) {
        this.idMouvementLotIngredient = idMouvementLotIngredient;
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
