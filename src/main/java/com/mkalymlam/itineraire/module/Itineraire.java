package com.mkalymlam.itineraire.module;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Time;
@Entity
@Table
public class Itineraire{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomZone")
    private String nomZone;

    @Column(name = "heureDebut")
    private Time heureDebut;

    @Column(name = "heureFin")
    private Time heureFin;

    public Long getId() {
        return id;
    }
    
    public String getNomZone() {
        return nomZone;
    }
    public Time getHeureDebut() {
        return heureDebut;
    }
    public Time getHeureFin() {
        return heureFin;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNomZone(String nomZone) {
        this.nomZone = nomZone;
    }
    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }
    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    
}
