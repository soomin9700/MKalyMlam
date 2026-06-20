package com.mkalymlam.recette.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.recette.model.RecetteBase;
import com.mkalymlam.recette.service.RecetteBaseService;

import java.util.List;

@Controller
@RequestMapping("/recetteBase")
public class RecetteBaseController {
    private final RecetteBaseService recetteBaseService;

    public RecetteBaseController(RecetteBaseService recetteBaseService) {
        this.recetteBaseService = recetteBaseService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<RecetteBase> recettes = recetteBaseService.findAll();
        model.addAttribute("recettes", recettes);
        return "recetteBase";
    }
    @GetMapping("/list/{id}")
    public String listById(@PathVariable("id") Long id, Model model) {
        RecetteBase recetteBase = recetteBaseService.find(id).orElse(null);
        model.addAttribute("recetteBase", recetteBase);
        return "recetteBaseFiltrer";
    }
    @PostMapping("/update")
    public String update(@RequestParam("id") Long id,
                         @RequestParam("id_produit") Long idProduit,
                         @RequestParam("id_ingredient") Long idIngredient,
                         @RequestParam("quantite_recette") Double quantiteRecette) {
        RecetteBase recetteBase = new RecetteBase();
        recetteBase.setId(id);
        recetteBase.setId_produit(idProduit);
        recetteBase.setId_ingredient(idIngredient);
        recetteBase.setQuantite_recette(quantiteRecette);
        recetteBaseService.update(recetteBase);
        return "redirect:/recetteBase/list";
    }

    @PostMapping("/save")
    public String save(@RequestParam("id_produit") Long idProduit,
                       @RequestParam("id_ingredient") Long idIngredient,
                       @RequestParam("quantite_recette") Double quantiteRecette) {
        RecetteBase recetteBase = new RecetteBase();
        recetteBase.setId_produit(idProduit);
        recetteBase.setId_ingredient(idIngredient);
        recetteBase.setQuantite_recette(quantiteRecette);
        recetteBaseService.save(recetteBase);
        return "redirect:/recetteBase/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        recetteBaseService.delete(id);
        return "redirect:/recetteBase/list";
    }
}
