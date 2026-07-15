package com.mkalymlam.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.service.IngredientService;

import jakarta.servlet.http.HttpServletResponse;

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


    @GetMapping("/export/csv")
public void exportCSV(HttpServletResponse response) throws IOException {
    List<Ingredient> ingredients = service.findAll(); // adaptez
    response.setContentType("text/csv; charset=UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=\"ingredients.csv\"");
    PrintWriter writer = response.getWriter();
    writer.println("ID,Nom,Seuil alerte,Unité");
    for (Ingredient i : ingredients) {
        writer.printf("%d,\"%s\",%s,\"%s\"%n",
                i.getIdIngredient(),
                i.getNomIngredient().replace("\"", "\"\""),
                i.getSeuilAlerteQuantite(),
                i.getUniteMesure()
        );
    }
    writer.flush();
}

@GetMapping("/print")
public String printPage(Model model) {
    model.addAttribute("ingredients", service.findAll());
    return "ingredient/print";
}

}