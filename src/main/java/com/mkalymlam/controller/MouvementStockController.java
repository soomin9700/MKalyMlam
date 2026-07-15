package com.mkalymlam.controller;

import com.mkalymlam.dto.MouvementStockDTO;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.MouvementStock;
import com.mkalymlam.service.MouvementStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mouvements")
public class MouvementStockController {

    private final MouvementStockService mouvementService;

    public MouvementStockController(MouvementStockService mouvementService) {
        this.mouvementService = mouvementService;
    }

    // =======================
    // Liste des mouvements de stock
    // =======================
    @GetMapping
    public String list(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) String typeMouvement,
            @RequestParam(required = false) Long idIngredient,
            Model model) {

        List<MouvementStockDTO> mouvements;

        // Vérifier si des filtres sont appliqués
        if (dateDebut != null || dateFin != null || typeMouvement != null || idIngredient != null) {
            mouvements = mouvementService.getMouvementsWithFilters(dateDebut, dateFin, typeMouvement, idIngredient);
        } else {
            mouvements = mouvementService.getAllMouvements();
        }

        // Statistiques
        long totalEntrees = mouvementService.countEntrees();
        long totalSorties = mouvementService.countSorties();
        long totalAjustements = mouvementService.countAjustements();

        // Liste des ingrédients pour le filtre
        List<Ingredient> ingredients = mouvementService.getAllIngredients();

        model.addAttribute("mouvements", mouvements);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("typeMouvement", typeMouvement);
        model.addAttribute("idIngredient", idIngredient);
        model.addAttribute("totalEntrees", totalEntrees);
        model.addAttribute("totalSorties", totalSorties);
        model.addAttribute("totalAjustements", totalAjustements);
        model.addAttribute("totalMouvements", mouvements.size());

        return "mouvement/list";
    }

    // =======================
    // Mouvements du jour
    // =======================
    @GetMapping("/jour")
    public String mouvementsDuJour(Model model) {
        List<MouvementStockDTO> mouvements = mouvementService.getMouvementsDuJour();

        model.addAttribute("mouvements", mouvements);
        model.addAttribute("titre", "Mouvements du jour");
        return "mouvement/list";
    }

    // =======================
    // Mouvements d'un ingrédient spécifique
    // =======================
    @GetMapping("/ingredient")
    public String mouvementsParIngredient(
            @RequestParam Long idIngredient,
            Model model) {

        List<MouvementStockDTO> mouvements = mouvementService.getMouvementsByIngredient(idIngredient);
        Ingredient ingredient = mouvementService.getAllIngredients()
                .stream()
                .filter(i -> i.getIdIngredient().equals(idIngredient))
                .findFirst()
                .orElse(null);

        model.addAttribute("mouvements", mouvements);
        model.addAttribute("titre", "Mouvements - " + (ingredient != null ? ingredient.getNomIngredient() : ""));
        return "mouvement/list";
    }
}