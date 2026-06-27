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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ingredients", service.getAll());
        return "ingredient/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/ingredients");
        return "ingredient/form";
    }

    @PostMapping
    public String create(
            @RequestParam String nomIngredient,
            @RequestParam Double seuilAlerteQuantite,
            @RequestParam String uniteMesure) {

        Ingredient ingredient = new Ingredient();

        ingredient.setNomIngredient(nomIngredient);
        ingredient.setSeuilAlerteQuantite(seuilAlerteQuantite);
        ingredient.setUniteMesure(uniteMesure);

        service.save(ingredient);

        return "redirect:/ingredients";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        model.addAttribute(
                "ingredient",
                service.getById(id));

        model.addAttribute("isEdit", true);
        model.addAttribute(
                "actionUrl",
                "/ingredients/" + id + "/edit");

        return "ingredient/form";
    }


    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String nomIngredient,
            @RequestParam Double seuilAlerteQuantite,
            @RequestParam String uniteMesure) {

        Ingredient ingredient =
                service.getById(id);

        ingredient.setNomIngredient(nomIngredient);
        ingredient.setSeuilAlerteQuantite(seuilAlerteQuantite);
        ingredient.setUniteMesure(uniteMesure);

        service.save(ingredient);

        return "redirect:/ingredients";
    }

    // Suppression
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/ingredients";
    }
}