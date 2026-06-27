package com.mkalymlam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idCommande\"")
    private Long idCommande;
    @Column(name = "\"idSession\"")
    private Long idSession;
    @Column(name = "\"idVendeuse\"")
    private Long idVendeuse;
    @Column(name = "\"idTypeCommande\"")
    private Long idTypeCommande;
    @Column(name = "\"dateHeureCreation\"")
    private LocalDateTime dateHeureCreation;
    @Column(name = "\"heureRecuperationPrevue\"")
    private LocalDateTime heureRecuperationPrevue;
    @Column(name = "\"lieuRecuperationPrevu\"")
    private LocalDateTime lieuRecuperationPrevu;
    @Column(name = "\"montantTotal\"")
    private double montantTotal;
    @Column(name = "\"idStatutCommande\"")
    private LocalDateTime idStatutCommande;
    @Column(name = "\"idTypeTarification\"")
    private LocalDateTime idTypeTarification;

    public Commande() {
    }


    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getIdVendeuse() {
        return idVendeuse;
    }

    public void setIdVendeuse(Long idVendeuse) {
        this.idVendeuse = idVendeuse;
    }


    public Long getIdTypeCommande() {
        return idTypeCommande;
    }

    public void setIdTypeCommande(Long idTypeCommande) {
        this.idTypeCommande = idTypeCommande;
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

    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
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

    public LocalDateTime getIdStatutCommande() {
        return idStatutCommande;
    }


    public void setIdStatutCommande(LocalDateTime idStatutCommande) {
        this.idStatutCommande = idStatutCommande;
    }

    public LocalDateTime getIdTypeTarification() {
        return idTypeTarification;
    }


    public void setIdTypeTarification(LocalDateTime idTypeTarification) {
        this.idTypeTarification = idTypeTarification;
    }
    
}
