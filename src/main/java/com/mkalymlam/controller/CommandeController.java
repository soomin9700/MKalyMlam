package com.mkalymlam.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.entity.Commande;
import com.mkalymlam.entity.StatutCommande;
import com.mkalymlam.service.CommandeService;
import com.mkalymlam.service.VenteService;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    private final VenteService venteService;
    private final CommandeService commandeService;

    public CommandeController(VenteService venteService, CommandeService commandeService) {
        this.venteService = venteService;
        this.commandeService = commandeService;
    }

    @PostMapping("/ajouter")
    public Commande ajouter(@RequestBody Commande commande) {
        commande.setDateHeureCreation(LocalDateTime.now());
        return venteService.ajouterCommande(commande);
    }

    @GetMapping("/montant")
    public double getMontant(@RequestParam Long id) {
        return venteService.getMontantCommande(id);
    }

    @PostMapping("/changerStatut")
    public Commande changerStatut(@RequestParam Long id_commande,
                                   @RequestParam String nouveau_statut) {
        StatutCommande statut = StatutCommande.valueOf(nouveau_statut);
        return commandeService.changerStatut(id_commande, statut);
    }

    @GetMapping("/liste")
    public List<Commande> listeCommandes() {
        return venteService.getAllCommandes();
    }
}
