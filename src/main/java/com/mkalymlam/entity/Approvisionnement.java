package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"approvisionnement\"")
public class Approvisionnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idApprovisionnement\"")
    private Long idApprovisionnement;

    @Column(name = "\"dateApprovisionnement\"")
    private LocalDate dateApprovisionnement;

    @Column(name = "\"coutTotalEstime\"")
    private Double coutTotalEstime;

    public Approvisionnement() {
    }

    public Long getIdApprovisionnement() {
        return idApprovisionnement;
    }

    public void setIdApprovisionnement(Long idApprovisionnement) {
        this.idApprovisionnement = idApprovisionnement;
    }

    public LocalDate getDateApprovisionnement() {
        return dateApprovisionnement;
    }

    public void setDateApprovisionnement(LocalDate dateApprovisionnement) {
        this.dateApprovisionnement = dateApprovisionnement;
    }

    public Double getCoutTotalEstime() {
        return coutTotalEstime;
    }

    public void setCoutTotalEstime(Double coutTotalEstime) {
        this.coutTotalEstime = coutTotalEstime;
    }
}
