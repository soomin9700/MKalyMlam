package com.mkalymlam.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.StatistiqueService;

@RestController
@RequestMapping("/statistiques")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/chiffreAffaire")
    public Double chiffreAffaire() {
        return statistiqueService.getChiffreAffaireGlobal();
    }

    @GetMapping("/benefice")
    public Double benefice() {
        return statistiqueService.getBeneficeTotal();
    }

    @GetMapping("/benefice/{idItineraire}")
    public Double beneficeParItineraire(@PathVariable Long idItineraire) {
        return statistiqueService.getBeneficeParIdItineraire(idItineraire);
    }

    @GetMapping("/graphique")
    public List<Map<String, Object>> graphique() {
        return statistiqueService.getDonneesGraphique();
    }
}
