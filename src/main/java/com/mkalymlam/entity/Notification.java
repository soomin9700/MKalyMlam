package com.mkalymlam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idNotification\"")
    private Long idNotification;

    @Column(name = "\"titre\"")
    private String titre;

    @Column(name = "\"contenu\"", length = 2000)
    private String contenu;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"type\"")
    private TypeNotification type;

    @Column(name = "\"dateCreation\"")
    private LocalDateTime dateCreation;

    @Column(name = "\"dateModification\"")
    private LocalDateTime dateModification;

    // Visible ou non par les clients (permet de préparer/dépublier sans supprimer)
    @Column(name = "\"publie\"")
    private Boolean publie;

    // Lien optionnel vers le produit annoncé 
    @ManyToOne
    @JoinColumn(name = "\"idProduit\"")
    private Produit produit;

    // Lien optionnel vers la session en cours 
    @ManyToOne
    @JoinColumn(name = "\"idSession\"")
    private SessionTruck session;

    // Auteur de la publication (administrateur ou employé)
    @ManyToOne
    @JoinColumn(name = "\"idUtilisateur\"")
    private Utilisateur auteur;

    public Notification() {}

    public Notification(String titre, String contenu, TypeNotification type) {
        this.titre = titre;
        this.contenu = contenu;
        this.type = type;
    }

    // Getters et Setters

    public Long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Long idNotification) {
        this.idNotification = idNotification;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public Boolean getPublie() {
        return publie;
    }

    public void setPublie(Boolean publie) {
        this.publie = publie;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public SessionTruck getSession() {
        return session;
    }

    public void setSession(SessionTruck session) {
        this.session = session;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }
}