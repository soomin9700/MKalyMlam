package com.mkalymlam.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Produit;
import com.mkalymlam.entity.ProduitAvecDisponibilite;
import com.mkalymlam.repository.ProduitAvecDisponibiliteRepository;
import com.mkalymlam.service.ProduitAvecDisponibiliteService;
import com.mkalymlam.service.ProduitService;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService service;
    private final ProduitAvecDisponibiliteRepository produitVueRepository;

    public ProduitController(ProduitService service, 
                             ProduitAvecDisponibiliteRepository produitVueRepository) {
        this.service = service;
        this.produitVueRepository = produitVueRepository;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String nomProduit,
            @RequestParam(required = false) Boolean nouveauProduit,
            @RequestParam(required = false) Boolean estDisponible,
            Model model) {

        // Utiliser directement la vue
        List<ProduitAvecDisponibilite> produitsVue = produitVueRepository.findByCriteria(
            nomProduit, estDisponible, nouveauProduit
        );

        // Convertir en Produit pour garder la compatibilité avec la JSP
        List<Produit> produits = produitsVue.stream()
                .map(p -> {
                    Produit produit = new Produit();
                    produit.setIdProduit(p.getIdProduit());
                    produit.setNomProduit(p.getNomProduit());
                    produit.setPrixBase(p.getPrixBase());
                    produit.setEstNouveau(p.getEstNouveau());
                    produit.setDateCreation(p.getDateCreation());
                    produit.setEstDisponible(p.getEstDisponible());
                    return produit;
                })
                .collect(Collectors.toList());

        model.addAttribute("produits", produits);
        model.addAttribute("totalProduits", produits.size());
        return "produit/list";
    }

    @GetMapping("/liste")
    @ResponseBody
    public List<Produit> liste() {
        return service.findAll();
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