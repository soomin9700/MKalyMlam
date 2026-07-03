package com.mkalymlam.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"commande\"")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idCommande\"")
    private Long idCommande;

    @Column(name = "\"dateHeureCreation\"")
    private LocalDateTime dateHeureCommande = LocalDateTime.now();

    @Column(name = "\"montantTotal\"")
    private Double montantTotal = 0.0;

    @Column(name = "\"idSession\"")
    private Integer idSession;

    @Column(name = "\"idVendeuse\"")
    private Integer idVendeuse;

    @Column(name = "\"idClient\"")
    private Integer idClient;

    @Column(name = "\"idStatutCommande\"")
    private Integer idStatutCommande;

    @Column(name = "\"idTypeTarification\"")
    private Integer idTypeTarification;

    @Column(name = "\"idTypeCommande\"")
    private Integer idTypeCommande;

    public Commande() {}

    // ==========================================
    // GETTERS ET SETTERS
    // ==========================================

    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }

    public LocalDateTime getDateHeureCommande() { return dateHeureCommande; }
    public void setDateHeureCommande(LocalDateTime dateHeureCommande) { this.dateHeureCommande = dateHeureCommande; }

    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) { this.montantTotal = montantTotal; }

    public Integer getIdSession() { return idSession; }
    public void setIdSession(Integer idSession) { this.idSession = idSession; }

    public Integer getIdVendeuse() { return idVendeuse; }
    public void setIdVendeuse(Integer idVendeuse) { this.idVendeuse = idVendeuse; }

    public Integer getIdClient() { return idClient; }
    public void setIdClient(Integer idClient) { this.idClient = idClient; }

    public Integer getIdStatutCommande() { return idStatutCommande; }
    public void setIdStatutCommande(Integer idStatutCommande) { this.idStatutCommande = idStatutCommande; }

    public Integer getIdTypeTarification() { return idTypeTarification; }
    public void setIdTypeTarification(Integer idTypeTarification) { this.idTypeTarification = idTypeTarification; }

    public Integer getIdTypeCommande() { return idTypeCommande; }
    public void setIdTypeCommande(Integer idTypeCommande) { this.idTypeCommande = idTypeCommande; }
}
