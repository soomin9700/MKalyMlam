package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.service.IngredientService;
import com.mkalymlam.service.LotIngredientService;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService service;
    private final LotIngredientService lotIngredientService;

    public IngredientController(IngredientService service, LotIngredientService lotIngredientService) {
        this.service = service;
        this.lotIngredientService = lotIngredientService;
    }

    // =======================
    // Liste des ingrédients
    // =======================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("ingredients", service.findAll());
        return "ingredient/list";
    }

    // =======================
    // Formulaire d'ajout
    // =======================
    @GetMapping("/new")
    public String createForm(Model model) {

        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/ingredients");

        return "ingredient/form";
    }

    // =======================
    // Enregistrer
    // =======================
    @PostMapping
    public String create(@ModelAttribute Ingredient ingredient) {

        service.save(ingredient);

        return "redirect:/ingredients";
    }

    // =======================
    // Formulaire de modification
    // =======================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        Ingredient ingredient = service.getById(id);

        model.addAttribute("ingredient", ingredient);
        model.addAttribute("isEdit", true);
        model.addAttribute("actionUrl", "/ingredients/" + id + "/edit");

        return "ingredient/form";
    }

    // =======================
    // Mise à jour
    // =======================
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute Ingredient ingredient) {

        ingredient.setIdIngredient(id);

        service.save(ingredient);

        return "redirect:/ingredients";
    }

    // =======================
    // Suppression
    // =======================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        service.deleteById(id);

        return "redirect:/ingredients";
    }

    @GetMapping("/bientot-perimes")
    public String bientotPerimes(Model model) {
        model.addAttribute("lots", lotIngredientService.getIngredientsBientotPerimes());
        model.addAttribute("ingredients", service.findAll());
        return "ingredient/ingredient-bientot-perimes";
    }

    @GetMapping("/bientot-perimes/{idIngredient}")
    public String bientotPerimesByIngredient(@PathVariable Long idIngredient, Model model) {
        Ingredient ingredient = service.getById(idIngredient);
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("lots", lotIngredientService.getIngredientsBientotPerimesByIdIngredient(idIngredient));
        return "ingredient/ingredient-bientot-perimes-by-idIngredient";
    }

}