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
@Table(name = "\"historiqueConsommation\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HistoriqueConsommation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idConsommation\"")
    private Long idConsommation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idCommande\"")
    private Commande commande;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idIngredient\"")
    private Ingredient ingredient;

    @Column(name = "\"quantiteConsommee\"")
    private Double quantiteConsommee;

    @Column(name = "\"dateConsommation\"")
    private LocalDateTime dateConsommation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"idSession\"")
    private SessionTruck session;

    public HistoriqueConsommation() {
    }

    public HistoriqueConsommation(Commande commande, Ingredient ingredient, Double quantiteConsommee, SessionTruck session) {
        this.commande = commande;
        this.ingredient = ingredient;
        this.quantiteConsommee = quantiteConsommee;
        this.session = session;
        this.dateConsommation = LocalDateTime.now();
    }

    public Long getIdConsommation() {
        return idConsommation;
    }

    public void setIdConsommation(Long idConsommation) {
        this.idConsommation = idConsommation;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantiteConsommee() {
        return quantiteConsommee;
    }

    public void setQuantiteConsommee(Double quantiteConsommee) {
        this.quantiteConsommee = quantiteConsommee;
    }

    public LocalDateTime getDateConsommation() {
        return dateConsommation;
    }

    public void setDateConsommation(LocalDateTime dateConsommation) {
        this.dateConsommation = dateConsommation;
    }

    public SessionTruck getSession() {
        return session;
    }

    public void setSession(SessionTruck session) {
        this.session = session;
    }
}
