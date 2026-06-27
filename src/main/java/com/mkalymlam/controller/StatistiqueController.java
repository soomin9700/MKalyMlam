package com.mkalymlam.controller;

import com.mkalymlam.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Double beneficeParItineraire( @PathVariable Long idItineraire) {
        return statistiqueService.getBeneficeByIdItineraire(idItineraire);
    }

    @GetMapping("/graphique")
    public List<Map<String, Object>> graphique() {
        return statistiqueService.getDonneesGraphique();
    }
}