package com.mkalymlam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "conge")
public class Conge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idEmploye\"", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employe employe;

    @Column(name = "\"dateDebut\"")
    private LocalDate dateDebut;

    @Column(name = "\"dateFin\"")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"typeConge\"")
    private TypeConge typeConge;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"statut\"")
    private StatutValidation statut = StatutValidation.EN_ATTENTE;

    public Conge() {}

    // Getters et setters (inchangés)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public TypeConge getTypeConge() { return typeConge; }
    public void setTypeConge(TypeConge typeConge) { this.typeConge = typeConge; }
    public StatutValidation getStatut() { return statut; }
    public void setStatut(StatutValidation statut) { this.statut = statut; }
}