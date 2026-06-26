package com.mkalymlam.itineraire.module;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "itineraire")
public class Itineraire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idItineraire\"")
    private Long id;

    @Column(name = "\"nomZone\"")
    private String nomZone;

    @Column(name = "\"lieuExact\"")
    private String lieuExact;

    @Column(name = "\"heureDebutPrevue\"")
    private Time heureDebutPrevue;

    @Column(name = "\"heureFinPrevue\"")
    private Time heureFinPrevue;

    @Column(name = "\"jourSemaine\"")
    private String jourSemaine;

    public Itineraire() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomZone() { return nomZone; }
    public void setNomZone(String nomZone) { this.nomZone = nomZone; }
    public String getLieuExact() { return lieuExact; }
    public void setLieuExact(String lieuExact) { this.lieuExact = lieuExact; }
    public Time getHeureDebutPrevue() { return heureDebutPrevue; }
    public void setHeureDebutPrevue(Time h) { this.heureDebutPrevue = h; }
    public Time getHeureFinPrevue() { return heureFinPrevue; }
    public void setHeureFinPrevue(Time h) { this.heureFinPrevue = h; }
    public String getJourSemaine() { return jourSemaine; }
    public void setJourSemaine(String jourSemaine) { this.jourSemaine = jourSemaine; }
}