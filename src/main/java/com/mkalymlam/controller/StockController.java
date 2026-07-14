package com.mkalymlam.controller;

import com.mkalymlam.dto.IngredientStockDTO;
import com.mkalymlam.dto.StockStatisticsDTO;
import com.mkalymlam.service.LotIngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/stocks")
public class StockController {

    private final LotIngredientService lotIngredientService;

    public StockController(LotIngredientService lotIngredientService) {
        this.lotIngredientService = lotIngredientService;
    }

    // =======================
    // Dashboard des stocks
    // =======================
    @GetMapping
    public String dashboard(Model model) {
        // Statistiques
        StockStatisticsDTO stats = lotIngredientService.getStockStatistics();
        model.addAttribute("stats", stats);

        // Top 5 des ingrédients avec leur stock
        List<IngredientStockDTO> topIngredients = lotIngredientService.getAllIngredientsWithStock()
            .stream()
            .limit(5)
            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("topIngredients", topIngredients);

        return "stock/dashboard";
    }

    // =======================
    // Liste des stocks des ingrédients
    // =======================
    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) String nomIngredient,
            @RequestParam(required = false) String statut,
            Model model) {

        List<IngredientStockDTO> ingredients;

        if (statut != null && !statut.isEmpty()) {
            // Filtrer par statut
            switch (statut) {
                case "DISPONIBLE":
                    ingredients = lotIngredientService.getIngredientsDisponibles();
                    break;
                case "RUPTURE":
                    ingredients = lotIngredientService.getIngredientsEnRupture();
                    break;
                case "ALERTE":
                    ingredients = lotIngredientService.getIngredientsEnAlerte();
                    break;
                default:
                    ingredients = lotIngredientService.getAllIngredientsWithStock();
            }
        } else if (nomIngredient != null && !nomIngredient.isEmpty()) {
            // Rechercher par nom
            ingredients = lotIngredientService.searchIngredientsWithStock(nomIngredient);
        } else {
            ingredients = lotIngredientService.getAllIngredientsWithStock();
        }

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("nomIngredient", nomIngredient);
        model.addAttribute("statut", statut);
        return "stock/list";
    }

    // =======================
    // Détail d'un ingrédient
    // =======================
    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        IngredientStockDTO ingredient = lotIngredientService.getStockByIngredientId(id);
        model.addAttribute("ingredient", ingredient);
        return "stock/detail";
    }
}