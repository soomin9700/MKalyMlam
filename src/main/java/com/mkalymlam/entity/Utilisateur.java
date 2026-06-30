package com.mkalymlam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idUtilisateur\"")
    private Long idUtilisateur;

    private String nom;
    private String prenom;
    private String email;

    @Column(name = "\"motDePasse\"")
    private String motDePasse;

    // Correction : le type est bien RoleEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idRole\"", nullable = false)
    private RoleEntity role;

    private Double salaireBaseFixe;

    @Column(name = "statut_actif")
    private Boolean statutActif = true;

    // Constructeur
    public Utilisateur() {
    }

    // --- getters & setters (attention au type de role) ---

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
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

    public Double getSalaireBaseFixe() {
        return salaireBaseFixe;
    }

    public void setSalaireBaseFixe(Double salaireBaseFixe) {
        this.salaireBaseFixe = salaireBaseFixe;
    }

    public Boolean getStatutActif() {
        return statutActif;
    }

    public void setStatutActif(Boolean statutActif) {
        this.statutActif = statutActif;
    }
}