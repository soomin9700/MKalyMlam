package com.mkalymlam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"notificationPlateforme\"")
public class NotificationPlateforme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idNotification\"")
    private Long idNotification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idTypeNotification\"")
    private TypeNotification typeNotification;

    @Column(name = "\"titre\"", nullable = false)
    private String titre;

    @Column(name = "\"message\"", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idProduitLie\"", referencedColumnName = "\"idProduit\"")
    private Produit produitLie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idSessionLiee\"", referencedColumnName = "\"idSession\"")
    private SessionTruck sessionLiee;

    @Column(name = "\"dateHeureEnvoi\"")
    private LocalDateTime dateHeureEnvoi;

    public NotificationPlateforme() {
    }

    public Long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Long idNotification) {
        this.idNotification = idNotification;
    }

    public TypeNotification getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(TypeNotification typeNotification) {
        this.typeNotification = typeNotification;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Produit getProduitLie() {
        return produitLie;
    }

    public void setProduitLie(Produit produitLie) {
        this.produitLie = produitLie;
    }

    public SessionTruck getSessionLiee() {
        return sessionLiee;
    }

    public void setSessionLiee(SessionTruck sessionLiee) {
        this.sessionLiee = sessionLiee;
    }

    public LocalDateTime getDateHeureEnvoi() {
        return dateHeureEnvoi;
    }

    public void setDateHeureEnvoi(LocalDateTime dateHeureEnvoi) {
        this.dateHeureEnvoi = dateHeureEnvoi;
    }
}
