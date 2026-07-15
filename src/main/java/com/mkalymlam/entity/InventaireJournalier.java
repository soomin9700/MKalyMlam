package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "\"inventaireJournalier\"")
public class InventaireJournalier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idInventaire\"")
    private Long idInventaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idSession\"", nullable = false) 
    private SessionTruck sessionTruck;

    @Column(name = "\"dateInventaire\"", nullable = false) 
    private LocalDate dateInventaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeItem\"", nullable = false) 
    private TypeItem typeItem;

    @Column(name = "\"idItem\"", nullable = false) 
    private Long idItem;

    @Column(name = "\"quantitePhysiqueConstatee\"", nullable = false) 
    private Double quantitePhysiqueConstatee;

    @Column(name = "\"quantiteTheoriqueSysteme\"", nullable = false) 
    private Double quantiteTheoriqueSysteme;

    @Column(name = "\"ecartInventaire\"", nullable = false) 
    private Double ecartInventaire;

    @Transient
    private String nomItem;

    // Constructeurs
    public InventaireJournalier() {
    }

    public InventaireJournalier(SessionTruck sessionTruck, LocalDate dateInventaire,
            TypeItem typeItem, Long idItem, Double quantitePhysiqueConstatee) {
        this.sessionTruck = sessionTruck;
        this.dateInventaire = dateInventaire;
        this.typeItem = typeItem;
        this.idItem = idItem;
        this.quantitePhysiqueConstatee = quantitePhysiqueConstatee;
        this.quantiteTheoriqueSysteme = 0.0;
        this.ecartInventaire = 0.0;
    }

    // Getters et Setters
    public Long getIdInventaire() {
        return idInventaire;
    }

    public void setIdInventaire(Long idInventaire) {
        this.idInventaire = idInventaire;
    }

    public SessionTruck getSessionTruck() {
        return sessionTruck;
    }

    public void setSessionTruck(SessionTruck sessionTruck) {
        this.sessionTruck = sessionTruck;
    }

    public LocalDate getDateInventaire() {
        return dateInventaire;
    }

    public void setDateInventaire(LocalDate dateInventaire) {
        this.dateInventaire = dateInventaire;
    }

    public TypeItem getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(TypeItem typeItem) {
        this.typeItem = typeItem;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Double getQuantitePhysiqueConstatee() {
        return quantitePhysiqueConstatee;
    }

    public void setQuantitePhysiqueConstatee(Double quantitePhysiqueConstatee) {
        this.quantitePhysiqueConstatee = quantitePhysiqueConstatee;
    }

    public Double getQuantiteTheoriqueSysteme() {
        return quantiteTheoriqueSysteme;
    }

    public void setQuantiteTheoriqueSysteme(Double quantiteTheoriqueSysteme) {
        this.quantiteTheoriqueSysteme = quantiteTheoriqueSysteme;
    }

    public Double getEcartInventaire() {
        return ecartInventaire;
    }

    public void setEcartInventaire(Double ecartInventaire) {
        this.ecartInventaire = ecartInventaire;
    }

    public String getNomItem() {
        return nomItem;
    }

    public void setNomItem(String nomItem) {
        this.nomItem = nomItem;
    }
}