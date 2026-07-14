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
@Table(name = "\"historiqueStatutCommande\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HistoriqueStatutCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idHistorique\"")
    private Long idHistorique;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idCommande\"")
    private Commande commande;

    @Column(name = "\"ancienStatut\"")
    private String ancienStatut;

    @Column(name = "\"nouveauStatut\"")
    private String nouveauStatut;

    @Column(name = "\"dateChangement\"")
    private LocalDateTime dateChangement;

    public HistoriqueStatutCommande() {
    }

    public HistoriqueStatutCommande(Commande commande, String ancienStatut, String nouveauStatut) {
        this.commande = commande;
        this.ancienStatut = ancienStatut;
        this.nouveauStatut = nouveauStatut;
        this.dateChangement = LocalDateTime.now();
    }

    public Long getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(Long idHistorique) {
        this.idHistorique = idHistorique;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getAncienStatut() {
        return ancienStatut;
    }

    public void setAncienStatut(String ancienStatut) {
        this.ancienStatut = ancienStatut;
    }

    public String getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(String nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public LocalDateTime getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDateTime dateChangement) {
        this.dateChangement = dateChangement;
    }
}
