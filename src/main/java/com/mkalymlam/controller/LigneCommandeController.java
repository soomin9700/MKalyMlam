package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.entity.LigneCommande;
import com.mkalymlam.service.VenteService;

@RestController
@RequestMapping("/ligneCommande")
public class LigneCommandeController {

    private final VenteService venteService;

    public LigneCommandeController(VenteService venteService) {
        this.venteService = venteService;
    }

    @PostMapping("/ajouter")
    public LigneCommande ajouter(@RequestBody LigneCommande ligne) {
        return venteService.ajouterLigneCommande(ligne);
    }

    @GetMapping("/montant")
    public double getMontant(@RequestParam Long idCommande) {
        return venteService.getMontantLignes(idCommande);
    }

    @GetMapping("/liste")
    public List<LigneCommande> getListe(@RequestParam Long idCommande) {
        return venteService.getLignesByCommande(idCommande);
    }
}
