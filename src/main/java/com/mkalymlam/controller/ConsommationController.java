package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.entity.HistoriqueConsommation;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.SessionTruckRepository;
import com.mkalymlam.service.ConsommationService;

@Controller
@RequestMapping("/consommation")
public class ConsommationController {

    private final ConsommationService consommationService;
    private final SessionTruckRepository sessionTruckRepository;
    private final IngredientRepository ingredientRepository;

    public ConsommationController(ConsommationService consommationService,
                                  SessionTruckRepository sessionTruckRepository,
                                  IngredientRepository ingredientRepository) {
        this.consommationService = consommationService;
        this.sessionTruckRepository = sessionTruckRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/historique")
    public String historique(@RequestParam(required = false) Long idSession,
                             @RequestParam(required = false) Long idIngredient,
                             Model model) {
        List<HistoriqueConsommation> historiques;
        if (idSession != null && idIngredient != null) {
            historiques = consommationService.getHistoriqueParSessionEtIngredient(idSession, idIngredient);
        } else if (idSession != null) {
            historiques = consommationService.getHistoriqueParSession(idSession);
        } else if (idIngredient != null) {
            historiques = consommationService.getHistoriqueParIngredient(idIngredient);
        } else {
            historiques = consommationService.getHistoriqueParSession(null);
        }

        List<SessionTruck> sessions = sessionTruckRepository.findAll();
        List<Ingredient> ingredients = ingredientRepository.findAll();

        model.addAttribute("historiques", historiques);
        model.addAttribute("sessions", sessions);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("idSessionSelectionne", idSession);
        model.addAttribute("idIngredientSelectionne", idIngredient);

        return "consommation/listeConsommations";
    }
}
