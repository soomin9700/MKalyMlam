package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;

import com.mkalymlam.entity.*;
import com.mkalymlam.service.*;


@Controller
@RequestMapping("/inventaire")
public class InventaireController {
    private final InventaireJournalierService service;

    public InventaireController(InventaireJournalierService service) {
        this.service = service;
    }
    
    @GetMapping("/findAll")
    public String findAll(Model model){
        List<InventaireJournalier> inventaires = service.getAll();
        model.addAttribute("inventaires", inventaires);
        return "inventaire/list";
    }

    @GetMapping("/list")
    public String listInventaires(
            Model model,
            @RequestParam(required = false) Long idSession,
            @RequestParam(required = false) Boolean ecart) {
        
        List<InventaireJournalier> inventaires;
        String titre = "Tous les inventaires";
        
        if (ecart != null && ecart) {
            inventaires = service.findByAvecEcart();
            titre = "Inventaires avec écarts";
        } else if (idSession != null) {
            // filtre par session
            inventaires = service.findBySession(idSession);
            titre = "Inventaires de la session #" + idSession;
        } else {
            // tous les inventaires
            inventaires = service.getAll();
        }
        
        model.addAttribute("inventaires", inventaires);
        model.addAttribute("titre", titre);
        model.addAttribute("idSessionFiltre", idSession);
        model.addAttribute("ecart", ecart);
        
        return "inventaire/list";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("sessions", service.getAllSessionTrucks());
        model.addAttribute("typeItems", service.getAllTypeItems());
        model.addAttribute("inventaire", new InventaireJournalier());
        model.addAttribute("actionUrl", "/inventaire/save");
        model.addAttribute("isEdit", false);
        return "inventaire/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        InventaireJournalier inventaire = service.getById(id);
        if (inventaire == null) {
            return "redirect:/inventaire/findAll";
        }
        
        model.addAttribute("sessions", service.getAllSessionTrucks());
        model.addAttribute("typeItems", service.getAllTypeItems());
        model.addAttribute("inventaire", inventaire);
        model.addAttribute("actionUrl", "/inventaire/update/" + id);
        model.addAttribute("isEdit", true);
        return "inventaire/form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute InventaireJournalier inventaire) {
        service.save(inventaire);
        return "redirect:/inventaire/findAll";
    }

    // @PostMapping("/update/{id}")
    // public String update(@PathVariable Long id, @ModelAttribute InventaireJournalier inventaire) {
    //     // Mettre à jour l'inventaire
    //     service.update(id, inventaire);
    //     return "redirect:/inventaire/findAll";
    // }

    // @PostMapping("/delete/{id}")
    // public String delete(@PathVariable Long id) {
    //     service.delete(id);
    //     return "redirect:/inventaire/findAll";
    // }

}
