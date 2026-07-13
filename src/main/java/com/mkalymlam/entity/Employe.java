package com.mkalymlam.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "employe")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matricule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idUtilisateur\"", nullable = false, unique = true)
     @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Utilisateur utilisateur;

    private String poste;
    private String departement;

    @Column(name = "\"dateEmbauche\"")   // <-- correction : échappe la casse
    private LocalDate dateEmbauche;

    @Column(name = "\"salaireBase\"")
    private Double salaireBase;

    public Employe() {}

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(Double salaireBase) {
        this.salaireBase = salaireBase;
    }
}