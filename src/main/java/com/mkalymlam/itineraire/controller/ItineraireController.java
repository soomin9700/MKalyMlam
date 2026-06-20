package com.mkalymlam.itineraire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.itineraire.module.Itineraire;
import com.mkalymlam.itineraire.service.ItineraireService;

import java.sql.Time;
import java.util.List;

@Controller
@RequestMapping("/itineraire")
public class ItineraireController {
    private final ItineraireService itineraireService;

    public ItineraireController(ItineraireService itineraireService) {
        this.itineraireService = itineraireService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<Itineraire> itineraires = itineraireService.findAll();
        model.addAttribute("itineraires", itineraires);
        return "itineraire";
    }

    @GetMapping("/list/{id}")
    public String listById(@PathVariable("id") Long id, Model model) {
        Itineraire itineraire = itineraireService.find(id);
        model.addAttribute("itineraire", itineraire);
        return "itineraireFiltrer";
    }

    @PostMapping("/save")
    public String save(@RequestParam("nomZone") String nomZone,
                       @RequestParam("heureDebut") String heureDebut,
                       @RequestParam("heureFin") String heureFin) {
        Itineraire itineraire = new Itineraire();
        itineraire.setNomZone(nomZone);
        itineraire.setHeureDebut(Time.valueOf(heureDebut + ":00"));
        itineraire.setHeureFin(Time.valueOf(heureFin + ":00"));
        itineraireService.save(itineraire);
        return "redirect:/itineraire/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        itineraireService.delete(id);
        return "redirect:/itineraire/list";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") Long id,
                            @RequestParam("nomZone") String nomZone,
                            @RequestParam("heureDebut") String heureDebut,
                            @RequestParam("heureFin") String heureFin) {
        Itineraire itineraire = new Itineraire();
        itineraire.setId(id);
        itineraire.setNomZone(nomZone);
        itineraire.setHeureDebut(Time.valueOf(heureDebut + ":00"));
        itineraire.setHeureFin(Time.valueOf(heureFin + ":00"));
        itineraireService.update(itineraire);
        return "redirect:/itineraire/list";

}
}
