package com.mkalymlam.entity;

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
@Table(name = "\"equipement\"")
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idEquipement\"")
    private Long idEquipement;

    @Column(name = "\"nomEquipement\"")
    private String nomEquipement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeEquipement\"")
    private TypeEquipement typeEquipement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idMethodeComptable\"")
    private MethodeComptable methodeComptable;

    @Column(name = "\"prixUnitaire\"")
    private Double prixUnitaire;

    @Column(name = "\"quantiteMin\"")
    private Double quantiteMin;

    public Equipement() {
    }

    public Equipement( String nomEquipement, TypeEquipement typeEquipement, MethodeComptable methodeComptable, Double prixUnitaire, Double quantiteMin) {
        this.nomEquipement = nomEquipement;
        this.typeEquipement = typeEquipement;
        this.methodeComptable = methodeComptable;
        this.prixUnitaire = prixUnitaire;
        this.quantiteMin = quantiteMin;
    }

    public Long getIdEquipement() {
        return idEquipement;
    }

    public void setIdEquipement(Long idEquipement) {
        this.idEquipement = idEquipement;
    }

    public String getNomEquipement() {
        return nomEquipement;
    }

    public void setNomEquipement(String nomEquipement) {
        this.nomEquipement = nomEquipement;
    }

    public TypeEquipement getTypeEquipement() {
        return typeEquipement;
    }

    public void setTypeEquipement(TypeEquipement typeEquipement) {
        this.typeEquipement = typeEquipement;
    }

    public MethodeComptable getMethodeComptable() {
        return methodeComptable;
    }

    public void setMethodeComptable(MethodeComptable methodeComptable) {
        this.methodeComptable = methodeComptable;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getQuantiteMin() {
        return quantiteMin;
    }

    public void setQuantiteMin(Double quantiteMin) {
        this.quantiteMin = quantiteMin;
    }
}