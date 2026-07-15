package com.mkalymlam.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.entity.Itineraire;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.repository.ItineraireRepository;
import com.mkalymlam.repository.SessionTruckRepository;
import com.mkalymlam.service.VenteService;

@Controller
@RequestMapping("/ventes")
public class VenteViewController {

    private final VenteService venteService;
    private final SessionTruckRepository sessionTruckRepository;
    private final ItineraireRepository itineraireRepository;

    public VenteViewController(VenteService venteService,
                               SessionTruckRepository sessionTruckRepository,
                               ItineraireRepository itineraireRepository) {
        this.venteService = venteService;
        this.sessionTruckRepository = sessionTruckRepository;
        this.itineraireRepository = itineraireRepository;
    }

    @GetMapping
    public String liste(@RequestParam(required = false) LocalDate dateDebut,
                        @RequestParam(required = false) LocalDate dateFin,
                        @RequestParam(required = false) Long idSession,
                        @RequestParam(required = false) String zone,
                        Model model) {

        LocalDateTime debut = dateDebut != null ? dateDebut.atStartOfDay() : null;
        LocalDateTime fin = dateFin != null ? dateFin.atTime(LocalTime.MAX) : null;

        model.addAttribute("ventes", venteService.listerVentes(debut, fin, idSession, zone));
        model.addAttribute("sessions", sessionTruckRepository.findAll());
        model.addAttribute("itineraires", itineraireRepository.findAll());
        model.addAttribute("dateDebutSelectionne", dateDebut);
        model.addAttribute("dateFinSelectionne", dateFin);
        model.addAttribute("idSessionSelectionne", idSession);
        model.addAttribute("zoneSelectionnee", zone);

        return "vente/listeVentes";
    }
}
