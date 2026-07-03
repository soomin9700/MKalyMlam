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
    @JoinColumn(name = "\"idSession\"")
    private SessionTruck sessionTruck;

    @Column(name = "\"dateInventaire\"")
    private LocalDate dateInventaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeItem\"")
    private TypeItem typeItem;

    @Column(name = "\"quantitePhysiqueConstatee\"")
    private Double quantitePhysiqueConstatee;

    @Column(name = "\"quantiteTheoriqueSysteme\"")
    private Double quantiteTheoriqueSysteme;

    @Column(name = "\"ecartInventaire\"")
    private Double ecartInventaire;


    public InventaireJournalier(Long idInventaire, SessionTruck sessionTruck, LocalDate dateInventaire,
            TypeItem typeItem, Double quantitePhysiqueConstatee, Double quantiteTheoriqueSysteme,
            Double ecartInventaire) {
        this.idInventaire = idInventaire;
        this.sessionTruck = sessionTruck;
        this.dateInventaire = dateInventaire;
        this.typeItem = typeItem;
        this.quantitePhysiqueConstatee = quantitePhysiqueConstatee;
        this.quantiteTheoriqueSysteme = quantiteTheoriqueSysteme;
        this.ecartInventaire = ecartInventaire;
    }

    public InventaireJournalier() {
    }
    

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

}
