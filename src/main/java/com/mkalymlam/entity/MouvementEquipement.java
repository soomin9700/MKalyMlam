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
@Table(name = "\"mouvementEquipement\"")
public class MouvementEquipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idMouvementEquipement\"")
    private Long idMouvementEquipement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeMouvement\"")
    private TypeMouvement typeMouvement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idEquipement\"")
    private Equipement equipement;

    @Column(name = "\"quantite\"")
    private Double quantite;

    @Column(name = "\"dateMouvement\"")
    private LocalDate dateMouvement;

    public MouvementEquipement() {
    }

    public MouvementEquipement(
            TypeMouvement typeMouvement,
            Equipement equipement,
            Double quantite,
            LocalDate dateMouvement) {

        this.typeMouvement = typeMouvement;
        this.equipement = equipement;
        this.quantite = quantite;
        this.dateMouvement = dateMouvement;
    }

    public Long getIdMouvementEquipement() {
        return idMouvementEquipement;
    }

    public void setIdMouvementEquipement(Long idMouvementEquipement) {
        this.idMouvementEquipement = idMouvementEquipement;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
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