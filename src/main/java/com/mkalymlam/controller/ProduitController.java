package com.mkalymlam.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Produit;
import com.mkalymlam.service.ProduitService;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService service;

    public ProduitController(ProduitService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("produits", service.getAll());
        return "produit/list";
    }
    
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/produits");
        return "produit/form";
    }

    @PostMapping
    public String create(
            @RequestParam String nomProduit,
            @RequestParam Double prixBase,
            @RequestParam(required = false)
            Boolean estNouveau) {

        Produit produit = new Produit();

        produit.setNomProduit(nomProduit);
        produit.setPrixBase(prixBase);
        produit.setEstNouveau(
                estNouveau != null ? estNouveau : false);

        produit.setDateCreation(LocalDate.now());

        service.save(produit);

        return "redirect:/produits";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "produit",
                service.getById(id));

        model.addAttribute("isEdit", true);

        model.addAttribute(
                "actionUrl",
                "/produits/" + id + "/edit");

        return "produit/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String nomProduit,
            @RequestParam Double prixBase,
            @RequestParam(required = false)
            Boolean estNouveau) {

        Produit produit =
                service.getById(id);

        produit.setNomProduit(nomProduit);
        produit.setPrixBase(prixBase);
        produit.setEstNouveau(
                estNouveau != null ? estNouveau : false);

        service.save(produit);

        return "redirect:/produits";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        service.deleteById(id);

        return "redirect:/produits";
    }
}