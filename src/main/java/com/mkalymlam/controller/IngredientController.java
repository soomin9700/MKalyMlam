package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.service.IngredientService;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
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

}