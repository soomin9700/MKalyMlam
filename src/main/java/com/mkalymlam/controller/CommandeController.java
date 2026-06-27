package com.mkalymlam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.entity.Commande;
import com.mkalymlam.service.VenteService;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    private final VenteService venteService;

    public CommandeController(VenteService venteService) {
        this.venteService = venteService;
    }

    @PostMapping("/ajouter")
    @ResponseBody
    public Commande ajouter(@RequestBody Commande commande) {
        return venteService.ajouterCommande(commande);
    }

    @GetMapping("/montant")
    public double getMontant(@RequestParam Long id) {
        return venteService.getMontantCommande(id);
    }
}
