package com.mkalymlam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;


@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    @Column(name = "\"montantTotal\"")
    private double montantTotal;
    @Column(name = "\"dateHeureCreation\"")
    private LocalDateTime date;

    public Commande() {
    }

    // public LocalDateTime getId() {
    //     return id;
    // }

    // public void setId(LocalDateTime id) {
    //     this.id = id;
    // }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
