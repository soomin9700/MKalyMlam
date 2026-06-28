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
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idCommande\"")
    private Long idCommande;

    @ManyToOne
    @JoinColumn(name = "\"idSession\"")
    private SessionTruck sessionTruck;

    @Column(name = "\"idVendeuse\"")
    private Long idVendeuse;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"typeCommande\"")
    private TypeCommande typeCommande;

    @Column(name = "\"dateHeureCreation\"")
    private LocalDateTime dateHeureCreation;

    @Column(name = "\"heureRecuperationPrevue\"")
    private LocalDateTime heureRecuperationPrevue;

    @Column(name = "\"lieuRecuperationPrevu\"")
    private String lieuRecuperationPrevu;

    @Column(name = "\"montantTotal\"")
    private double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"statutCommande\"")
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

    public SessionTruck getSessionTruck() {
        return sessionTruck;
    }

    public void setSessionTruck(SessionTruck sessionTruck) {
        this.sessionTruck = sessionTruck;
    }

    public Long getIdVendeuse() {
        return idVendeuse;
    }

    public void setIdVendeuse(Long idVendeuse) {
        this.idVendeuse = idVendeuse;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
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

    public String getLieuRecuperationPrevu() {
        return lieuRecuperationPrevu;
    }

    public void setLieuRecuperationPrevu(String lieuRecuperationPrevu) {
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
}
