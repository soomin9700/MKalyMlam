package com.mkalymlam.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "absenceConge")
public class AbsenceConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idAbsence\"")
    private Long idAbsence;

    @ManyToOne
    @JoinColumn(name = "\"idUtilisateur\"")
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"typeConge\"")
    private TypeAbsence type;

    @Column(name = "\"dateDebut\"")
    private LocalDate dateDebut;

    @Column(name = "\"dateFin\"")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"statutValidation\"")
    private StatutValidation statutValidation = StatutValidation.EN_ATTENTE;

    public AbsenceConge() {
    }

    // GETTERS & SETTERS COMPLETS

    public Long getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(Long idAbsence) {
        this.idAbsence = idAbsence;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public TypeAbsence getType() {
        return type;
    }

    public void setType(TypeAbsence type) {
        this.type = type;
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

    public StatutValidation getStatutValidation() {
        return statutValidation;
    }

    public void setStatutValidation(StatutValidation statutValidation) {
        this.statutValidation = statutValidation;
    }


 


}