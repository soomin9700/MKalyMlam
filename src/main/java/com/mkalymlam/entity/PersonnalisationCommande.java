package com.mkalymlam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"personnalisationCommande\"")
public class PersonnalisationCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idPersonnalisation\"")
    private Long idPersonnalisation;

    @Column(name = "\"idLigne\"")
    private Long idLigne;

    @Column(name = "\"idIngredient\"")
    private Long idIngredient;

    @Column(name = "\"idActionCommande\"")
    private Long idActionCommande;

    @Column(name = "\"quantiteAjustee\"")
    private Double quantiteAjustee;

    public PersonnalisationCommande() {
    }

    public Long getIdPersonnalisation() {
        return idPersonnalisation;
    }

    public void setIdPersonnalisation(Long idPersonnalisation) {
        this.idPersonnalisation = idPersonnalisation;
    }

    public Long getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Long idLigne) {
        this.idLigne = idLigne;
    }

    public Long getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Long idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Long getIdActionCommande() {
        return idActionCommande;
    }

    public void setIdActionCommande(Long idActionCommande) {
        this.idActionCommande = idActionCommande;
    }

    public Double getQuantiteAjustee() {
        return quantiteAjustee;
    }

    public void setQuantiteAjustee(Double quantiteAjustee) {
        this.quantiteAjustee = quantiteAjustee;
    }
}
