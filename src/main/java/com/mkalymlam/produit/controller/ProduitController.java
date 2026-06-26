package com.mkalymlam.produit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.produit.model.Produit;
import com.mkalymlam.produit.service.ProduitService;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/liste")
    public String liste(Model model) {
        model.addAttribute("produits", produitService.findAll());
        return "produit/liste";
    }

    @GetMapping("/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("produit", new Produit());
        return "produit/formulaire";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Produit produit) {
        produitService.save(produit);
        return "redirect:/produit/liste";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("produit", produitService.findById(id));
        return "produit/formulaire";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        produitService.delete(id);
        return "redirect:/produit/liste";
    }

}