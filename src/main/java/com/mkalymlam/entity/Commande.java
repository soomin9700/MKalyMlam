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
@Table(name = "commande")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idCommande\"")
    private Long idCommande;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idSession\"")
    private SessionTruck sessionTruck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idVendeuse\"")
    private Utilisateur vendeuse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeCommande\"")
    private TypeCommande typeCommande;

    @Column(name = "\"dateHeureCreation\"")
    private LocalDateTime dateHeureCreation;
    @Column(name = "\"heureRecuperationPrevue\"")
    private LocalDateTime heureRecuperationPrevue;
    @Column(name = "\"lieuRecuperationPrevu\"")
    private LocalDateTime lieuRecuperationPrevu;
    @Column(name = "\"montantTotal\"")
    private double montantTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idStatutCommande\"")
    private StatutCommande statutCommande;
    @Column(name = "\"idTypeTarification\"")
    private Long idTypeTarification;

    public Commande() {
    }


    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public LocalDateTime getDateHeureCreation() {
        return dateHeureCreation;
    }

    public void setDateHeureCreation(LocalDateTime dateHeureCreation) {
        this.dateHeureCreation = dateHeureCreation;
    }

    public LocalDateTime getHeureRecuperationPrevue() {
        return heureRecuperationPrevue;
    }


    public void setHeureRecuperationPrevue(LocalDateTime heureRecuperationPrevue) {
        this.heureRecuperationPrevue = heureRecuperationPrevue;
    }

    public LocalDateTime getLieuRecuperationPrevu() {
        return lieuRecuperationPrevu;
    }


    public void setLieuRecuperationPrevu(LocalDateTime lieuRecuperationPrevu) {
        this.lieuRecuperationPrevu = lieuRecuperationPrevu;
    }

    public StatutCommande getStatutCommande() {
        return statutCommande;
    }


    public void setStatutCommande(StatutCommande statutCommande) {
        this.statutCommande = statutCommande;
    }

    public Long getIdTypeTarification() {
        return idTypeTarification;
    }


    public void setIdTypeTarification(Long idTypeTarification) {
        this.idTypeTarification = idTypeTarification;
    }


    public SessionTruck getSessionTruck() {
        return sessionTruck;
    }


    public void setSessionTruck(SessionTruck sessionTruck) {
        this.sessionTruck = sessionTruck;
    }


    public Utilisateur getVendeuse() {
        return vendeuse;
    }


    public void setVendeuse(Utilisateur vendeuse) {
        this.vendeuse = vendeuse;
    }


    public TypeCommande getTypeCommande() {
        return typeCommande;
    }


    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }
    
}
