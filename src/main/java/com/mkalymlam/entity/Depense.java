package com.mkalymlam.entity;

import java.time.LocalDate;

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
@Table(name = "\"depense\"")
public class Depense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idDepense\"")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idSession\"")
    private SessionTruck session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idTypeDepense\"")
    private TypeDepense typeDepense;

    @Column(name = "\"montantDepense\"")
    private Double montantDepense;

    @Column(name = "\"raisonDetaillee\"")
    private String raisonDetaillee;

    @Column(name = "\"dateDepense\"")
    private LocalDate dateDepense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"idStatutValidationAdmin\"")
    private StatutValidationAdmin statutValidationAdmin;

    @Column(name = "\"commentaireAdminRetour\"")
    private String commentaireAdminRetour;

    public Depense() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SessionTruck getSession() { return session; }
    public void setSession(SessionTruck session) { this.session = session; }
    public TypeDepense getTypeDepense() { return typeDepense; }
    public void setTypeDepense(TypeDepense typeDepense) { this.typeDepense = typeDepense; }
    public Double getMontantDepense() { return montantDepense; }
    public void setMontantDepense(Double montantDepense) { this.montantDepense = montantDepense; }
    public String getRaisonDetaillee() { return raisonDetaillee; }
    public void setRaisonDetaillee(String raisonDetaillee) { this.raisonDetaillee = raisonDetaillee; }
    public LocalDate getDateDepense() { return dateDepense; }
    public void setDateDepense(LocalDate dateDepense) { this.dateDepense = dateDepense; }
    public StatutValidationAdmin getStatutValidationAdmin() { return statutValidationAdmin; }
    public void setStatutValidationAdmin(StatutValidationAdmin statutValidationAdmin) { this.statutValidationAdmin = statutValidationAdmin; }
    public String getCommentaireAdminRetour() { return commentaireAdminRetour; }
    public void setCommentaireAdminRetour(String commentaireAdminRetour) { this.commentaireAdminRetour = commentaireAdminRetour; }
}
