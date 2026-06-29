package com.mkalymlam.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.service.StatistiqueService;

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

    @GetMapping("chiffreAffaire/parSession/{idSession}")
    public Double chifferAffaireParSession( @PathVariable Long idSession) {
        return statistiqueService.getChiffreAffaireByIdSession(idSession);
    }

    @GetMapping("/chiffreAffaire/parZone/{nomZone}")
    public Double chifferAffaireParZone( @PathVariable String nomZone) {
        return statistiqueService.getChiffreAffaireByZone(nomZone);
    }

    @GetMapping("chiffreAffaire/parSession/hebdomadaire/{idSession}")
    public Double chifferAffaireParSessionHebdomadaire( @PathVariable Long idSession) {
        return statistiqueService.getChiffreAffaireByIdSessionHebdomadaire(idSession);
    }

    @GetMapping("chiffreAffaire/parSession/{idSession}/{date1}/{date2")
    public Double chifferAffaireParSession2Dates(@PathVariable Long idSession, @PathVariable LocalDateTime date1, @PathVariable LocalDateTime date2) {
        return statistiqueService.getChiffreAffaireByIdSessionDates(idSession, date1, date2);
    }

    @GetMapping("chiffreAffaire/parSession/mensuel/{idSession}")
    public Double chifferAffaireParSessionMensuel( @PathVariable Long idSession) {
        return statistiqueService.getChiffreAffaireByIdSessionMensuel(idSession);
    }
}