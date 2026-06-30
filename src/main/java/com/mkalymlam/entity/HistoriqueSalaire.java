package com.mkalymlam.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historiqueSalaire")
public class HistoriqueSalaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idHistorique\"")
    private Long idHistorique;

    @ManyToOne
    @JoinColumn(name = "\"idUtilisateur\"")
    private Utilisateur utilisateur;

    @Column(name = "\"salaireBase\"")
    private Double salaireBase;

    @Column(name = "\"dateDebut\"")
    private LocalDate dateDebut;

    @Column(name = "\"dateFin\"")
    private LocalDate dateFin;

    public HistoriqueSalaire() {}

    public Long getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(Long idHistorique) {
        this.idHistorique = idHistorique;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(Double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    // getters/setters
}