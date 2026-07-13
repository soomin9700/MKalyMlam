package com.mkalymlam.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class NotificationPlateforme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idNotification\"")
    private Long idNotification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idTypeNotification\"")
    private TypeNotification typeNotification;

    @Column(name = "\"titre\"")
    private String titre;

    @Column(name = "\"message\"")
    private String message;

    @Column(name = "\"idProduitLie\"")
    private Long idProduitLie;

    @Column(name = "\"idSessionLiee\"")
    private Long idSessionLiee;

    @Column(name = "\"dateHeureEnvoi\"")
    private LocalDateTime dateHeureEnvoi;

    public NotificationPlateforme() {
    }

    public NotificationPlateforme(TypeNotification typeNotification, String titre, String message) {
        this.typeNotification = typeNotification;
        this.titre = titre;
        this.message = message;
        this.dateHeureEnvoi = LocalDateTime.now();
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

    public Long getIdProduitLie() {
        return idProduitLie;
    }

    public void setIdProduitLie(Long idProduitLie) {
        this.idProduitLie = idProduitLie;
    }

    public Long getIdSessionLiee() {
        return idSessionLiee;
    }

    public void setIdSessionLiee(Long idSessionLiee) {
        this.idSessionLiee = idSessionLiee;
    }

    public LocalDateTime getDateHeureEnvoi() {
        return dateHeureEnvoi;
    }

    public void setDateHeureEnvoi(LocalDateTime dateHeureEnvoi) {
        this.dateHeureEnvoi = dateHeureEnvoi;
    }
}
