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
@Table(name = "\"factureRecu\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FactureRecu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idFacture\"")
    private Long idFacture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idCommande\"")
    private Commande commande;

    @Column(name = "\"referenceFacture\"")
    private String referenceFacture;

    @Column(name = "\"dateFacturation\"")
    private LocalDateTime dateFacturation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idModePaiement\"")
    private ModePaiement modePaiement;

    @Column(name = "\"detailsTaxesBrut\"")
    private Double detailsTaxesBrut;

    public FactureRecu() {
    }

    public Long getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Long idFacture) {
        this.idFacture = idFacture;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getReferenceFacture() {
        return referenceFacture;
    }

    public void setReferenceFacture(String referenceFacture) {
        this.referenceFacture = referenceFacture;
    }

    public LocalDateTime getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(LocalDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Double getDetailsTaxesBrut() {
        return detailsTaxesBrut;
    }

    public void setDetailsTaxesBrut(Double detailsTaxesBrut) {
        this.detailsTaxesBrut = detailsTaxesBrut;
    }
}
