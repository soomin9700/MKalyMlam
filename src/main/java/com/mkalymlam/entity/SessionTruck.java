package com.mkalymlam.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"sessionTruck\"")
public class SessionTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idSession\"")
    private Long idSession;

    @Column(name = "\"idTruck\"")
    private Long idTruck;

    @Column(name = "\"idItineraire\"")
    private Long idItineraire;

    @Column(name = "\"dateSession\"")
    private LocalDate dateSession;

    @Column(name = "\"fondDeCaisseOuverture\"")
    private Double fondDeCaisseOuverture;

    @Column(name = "\"fondDeCaisseCloture\"")
    private Double fondDeCaisseCloture;

    @Column(name = "\"chiffreAffaireTotal\"")
    private Double chiffreAffaireTotal;

    @Column(name = "\"commissionTotaleEquipe\"")
    private Double commissionTotaleEquipe;

    @Column(name = "\"idStatutSession\"")
    private Long idStatutSession;

    public SessionTruck() {}

    public Long getIdSession() { return idSession; }
    public void setIdSession(Long idSession) { this.idSession = idSession; }
    public Long getIdTruck() { return idTruck; }
    public void setIdTruck(Long idTruck) { this.idTruck = idTruck; }
    public Long getIdItineraire() { return idItineraire; }
    public void setIdItineraire(Long idItineraire) { this.idItineraire = idItineraire; }
    public LocalDate getDateSession() { return dateSession; }
    public void setDateSession(LocalDate dateSession) { this.dateSession = dateSession; }
    public Double getFondDeCaisseOuverture() { return fondDeCaisseOuverture; }
    public void setFondDeCaisseOuverture(Double fondDeCaisseOuverture) { this.fondDeCaisseOuverture = fondDeCaisseOuverture; }
    public Double getFondDeCaisseCloture() { return fondDeCaisseCloture; }
    public void setFondDeCaisseCloture(Double fondDeCaisseCloture) { this.fondDeCaisseCloture = fondDeCaisseCloture; }
    public Double getChiffreAffaireTotal() { return chiffreAffaireTotal; }
    public void setChiffreAffaireTotal(Double chiffreAffaireTotal) { this.chiffreAffaireTotal = chiffreAffaireTotal; }
    public Double getCommissionTotaleEquipe() { return commissionTotaleEquipe; }
    public void setCommissionTotaleEquipe(Double commissionTotaleEquipe) { this.commissionTotaleEquipe = commissionTotaleEquipe; }
    public Long getIdStatutSession() { return idStatutSession; }
    public void setIdStatutSession(Long idStatutSession) { this.idStatutSession = idStatutSession; }
}
