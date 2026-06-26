package com.mkalymlam.ingredient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.ingredient.model.Ingredient;
import com.mkalymlam.ingredient.service.IngredientService;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/liste")
    public String liste(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredient/liste";
    }

    @GetMapping("/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient/formulaire";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Ingredient ingredient) {
        ingredientService.save(ingredient);
        return "redirect:/ingredient/liste";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findById(id));
        return "ingredient/formulaire";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return "redirect:/ingredient/liste";
    }
}