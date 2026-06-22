package com.mkalymlam.itineraire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mkalymlam.itineraire.module.Itineraire;
import com.mkalymlam.itineraire.service.ItineraireService;
import java.sql.Time;

@Controller
@RequestMapping("/itineraire")
public class ItineraireController {

    private final ItineraireService itineraireService;

    public ItineraireController(ItineraireService itineraireService) {
        this.itineraireService = itineraireService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("itineraires", itineraireService.findAll());
        return "itineraire";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("nomZone") String nomZone,
            @RequestParam("lieuExact") String lieuExact,
            @RequestParam("heureDebutPrevue") String heureDebut,
            @RequestParam("heureFinPrevue") String heureFin,
            @RequestParam("jourSemaine") String jourSemaine) {

        Itineraire it = new Itineraire();
        it.setNomZone(nomZone);
        it.setLieuExact(lieuExact);
        it.setHeureDebutPrevue(Time.valueOf(heureDebut + ":00"));
        it.setHeureFinPrevue(Time.valueOf(heureFin + ":00"));
        it.setJourSemaine(jourSemaine);
        itineraireService.save(it);
        return "redirect:/itineraire/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        itineraireService.delete(id);
        return "redirect:/itineraire/list";
    }
}