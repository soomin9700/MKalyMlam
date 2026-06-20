package com.mkalymlam.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.models.Commande;
import com.mkalymlam.services.VenteService;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    private final VenteService venteService;

    public CommandeController(VenteService venteService) {
        this.venteService = venteService;
    }

    @PostMapping("/ajouter")
    public Commande ajouter(@RequestBody Commande commande) {
        return venteService.ajouterCommande(commande);
    }

    @GetMapping("/montant")
    public double getMontant(@RequestParam Long id) {
        return venteService.getMontantCommande(id);
    }
}
