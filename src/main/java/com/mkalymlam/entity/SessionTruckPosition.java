package com.mkalymlam.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "\"sessionTruckPosition\"")
public class SessionTruckPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idSessionTruckPosition\"")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "\"idSession\"", nullable = false)
    private SessionTruck sessionTruck;

    @ManyToOne
    @JoinColumn(name = "\"idItineraire\"", nullable = false)
    private Itineraire itineraire;

    @Column(name = "\"heureArrivee\"", nullable = false)
    private LocalTime heureArrivee;

    @Column(name = "\"datePublication\"")
    private LocalDate datePublication;

    // Constructeurs
    public SessionTruckPosition() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SessionTruck getSessionTruck() { return sessionTruck; }
    public void setSessionTruck(SessionTruck sessionTruck) { this.sessionTruck = sessionTruck; }

    public Itineraire getItineraire() { return itineraire; }
    public void setItineraire(Itineraire itineraire) { this.itineraire = itineraire; }

    public LocalTime getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(LocalTime heureArrivee) { this.heureArrivee = heureArrivee; }

    public LocalDate getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDate datePublication) { this.datePublication = datePublication; }
}