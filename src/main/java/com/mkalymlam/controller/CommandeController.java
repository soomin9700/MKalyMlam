package com.mkalymlam.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;
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
    public Commande ajouter(@RequestBody Commande commande) {
        commande.setDateHeureCommande(LocalDateTime.now());
        
        if (commande.getIdSession() == null) commande.setIdSession(1);
        if (commande.getIdStatutCommande() == null) commande.setIdStatutCommande(1);
        if (commande.getIdTypeTarification() == null) commande.setIdTypeTarification(1);
        if (commande.getIdTypeCommande() == null) commande.setIdTypeCommande(1);
        if (commande.getIdVendeuse() == null) commande.setIdVendeuse(1);
        if (commande.getIdClient() == null) commande.setIdClient(1);

        return venteService.ajouterCommande(commande);
    }

    @GetMapping("/montant")
    public double getMontant(@RequestParam Long id) {
        return venteService.getMontantCommande(id);
    }

    // AJOUT DE LA MÉTHODE DE VALIDATION
    @PostMapping("/valider")
    @ResponseBody
    public String valider(@RequestParam("id") Long id, @RequestParam("montant") Double montant) {
        // 1. Récupère la commande existante via ton service
        Commande cmd = venteService.findById(id);
        
        if (cmd != null) {
            // 2. Met à jour le statut (ex: 2 = Payé/Validé) et le montant final
            cmd.setIdStatutCommande(2); 
            cmd.setMontantTotal(montant);
            
            // 3. Sauvegarde les changements
            venteService.save(cmd); // Assure-toi que cette méthode existe dans ton service
            
            return "OK";
        }
        
        return "ERREUR";
    }
}