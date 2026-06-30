package com.mkalymlam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.entity.FactureRecu;
import com.mkalymlam.entity.ModePaiement;
import com.mkalymlam.service.FactureRecuService;

@RestController
@RequestMapping("/facture")
public class FactureRecuController {

    private final FactureRecuService factureRecuService;

    public FactureRecuController(FactureRecuService factureRecuService) {
        this.factureRecuService = factureRecuService;
    }

    @PostMapping("/generer")
    public Object generer(@RequestParam Long id_commande,
                           @RequestParam String mode_paiement) {
        try {
            ModePaiement mode = ModePaiement.valueOf(mode_paiement);
            FactureRecu facture = factureRecuService.genererFacture(id_commande, mode);

            Map<String, Object> resultat = new HashMap<>();
            resultat.put("success", true);
            resultat.put("facture", facture);
            resultat.put("message", "Facture générée avec succès");
            return resultat;
        } catch (Exception e) {
            Map<String, Object> erreur = new HashMap<>();
            erreur.put("success", false);
            erreur.put("message", e.getMessage());
            return erreur;
        }
    }

    @GetMapping("/find")
    public FactureRecu find(@RequestParam Long id) {
        return factureRecuService.getFacture(id);
    }

    @GetMapping("/findAll")
    public List<FactureRecu> findAll() {
        return factureRecuService.getAllFactures();
    }

    @GetMapping("/byCommande")
    public FactureRecu byCommande(@RequestParam Long id_commande) {
        return factureRecuService.getFactureByCommande(id_commande);
    }
}
