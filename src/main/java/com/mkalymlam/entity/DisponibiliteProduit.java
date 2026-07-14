package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"disponibiliteProduit\"" )
public class DisponibiliteProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private Long id;

    @Column(name = "\"idProduit\"")
    private Long idProduit;

    @Column(name = "\"dateModification\"")
    private LocalDate dateModification;

    @Column(name = "\"estDisponible\"")
    private Boolean estDisponible;

    public DisponibiliteProduit(Long id, Long idProduit, LocalDate dateModification, Boolean estDisponible) {
        this.id = id;
        this.idProduit = idProduit;
        this.dateModification = dateModification;
        this.estDisponible = estDisponible;
    }

    public DisponibiliteProduit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Boolean getEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(Boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    

    
}