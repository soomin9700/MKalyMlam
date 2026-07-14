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

import java.math.BigDecimal;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idUtilisateur\"")
    private Integer idUtilisateur;

    @Column(name = "\"nom\"")
    private String nom;

    @Column(name = "\"prenom\"")
    private String prenom;

    @Column(name = "\"email\"")
    private String email;

    @Column(name = "\"motDePasse\"")
    private String motDePasse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idRole\"", nullable = false)
    private RoleEntity role;

    @Column(name = "\"salaireBaseFixe\"")
    private BigDecimal salaireBaseFixe;

    @Column(name = "\"statutActif\"")
    private Boolean statutActif = true;

    public Utilisateur() {
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public Integer getIdRole() {
        return role != null ? role.getIdRole().intValue() : null;
    }

    public BigDecimal getSalaireBaseFixe() {
        return salaireBaseFixe;
    }

    public void setSalaireBaseFixe(BigDecimal salaireBaseFixe) {
        this.salaireBaseFixe = salaireBaseFixe;
    }

    public Boolean getStatutActif() {
        return statutActif;
    }

    public void setStatutActif(Boolean statutActif) {
        this.statutActif = statutActif;
    }
}
