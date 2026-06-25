package com.mkalymlam.recette.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mkalymlam.ingredient.service.IngredientService;
import com.mkalymlam.produit.service.ProduitService;
import com.mkalymlam.recette.model.RecetteBase;
import com.mkalymlam.recette.service.RecetteBaseService;

@Controller
@RequestMapping("/recetteBase")
public class RecetteBaseController {

    private final RecetteBaseService recetteBaseService;
    private final ProduitService produitService;
    private final IngredientService ingredientService;

    public RecetteBaseController(
            RecetteBaseService recetteBaseService,
            ProduitService produitService,
            IngredientService ingredientService) {

        this.recetteBaseService = recetteBaseService;
        this.produitService = produitService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("recettes", recetteBaseService.findAll());
        model.addAttribute("produits", produitService.findAll());
        model.addAttribute("ingredients", ingredientService.findAll());
        return "recetteBase";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "OK";
    }

    @PostMapping("/save")
    public String save(
        @RequestParam("idProduit") Long idProduit,
        @RequestParam("idIngredient") Long idIngredient,
        @RequestParam("quantiteRecette") Double quantiteRecette) {

    RecetteBase recette = new RecetteBase();

    recette.setIdProduit(idProduit);
    recette.setIdIngredient(idIngredient);
    recette.setQuantiteRecette(quantiteRecette);

    recetteBaseService.save(recette);

    return "redirect:/recetteBase/list";
    }

    @GetMapping("/delete")
    public String delete(
        @RequestParam("idProduit") Long idProduit,
        @RequestParam("idIngredient") Long idIngredient) {

    recetteBaseService.delete(idProduit, idIngredient);

    return "redirect:/recetteBase/list";
    }
}