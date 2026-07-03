package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


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

    private Double quantitePhysiqueConstatee;

    private Double quantiteTheoriqueSysteme;

    private Double ecartInventaire;
    


}
