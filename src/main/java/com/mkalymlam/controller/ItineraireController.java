package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mkalymlam.entity.Itineraire;
import com.mkalymlam.service.ItineraireService;

@Controller
@RequestMapping("/itineraire")
public class ItineraireController {

    private final ItineraireService service;

    public ItineraireController(ItineraireService service) {
        this.service = service;
    }

    // ============================
    // Liste des itinéraires
    // ============================

    @GetMapping
    public String list(Model model) {

        model.addAttribute("itineraires", service.findAll());

        return "itineraire/list";
    }

    // ============================
    // Formulaire d'ajout
    // ============================

    @GetMapping("/new")
    public String newForm(Model model) {

        model.addAttribute("itineraire", new Itineraire());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl",
                "/itineraire");

        return "itineraire/form";
    }

    // ============================
    // Enregistrer
    // ============================

    @PostMapping
    public String save(@ModelAttribute Itineraire itineraire) {

        service.save(itineraire);

        return "redirect:/itineraire";
    }

    // ============================
    // Formulaire de modification
    // ============================

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model) {

        Itineraire itineraire = service.find(id);

        model.addAttribute("itineraire", itineraire);
        model.addAttribute("isEdit", true);
        model.addAttribute("actionUrl",
                "/itineraire/" + id + "/edit");

        return "itineraire/form";
    }

    // ============================
    // Mise à jour
    // ============================

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute Itineraire itineraire) {

        itineraire.setId(id);

        service.save(itineraire);

        return "redirect:/itineraire";
    }

    // ============================
    // Suppression
    // ============================

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        service.delete(id);

        return "redirect:/itineraire";
    }

}