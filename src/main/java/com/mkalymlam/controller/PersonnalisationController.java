package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.entity.ActionCommande;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.PersonnalisationCommande;
import com.mkalymlam.service.PersonnalisationService;

@RestController
@RequestMapping("/personnalisation")
public class PersonnalisationController {

    private final PersonnalisationService service;

    public PersonnalisationController(PersonnalisationService service) {
        this.service = service;
    }

    @PostMapping("/ajouter")
    @ResponseBody
    public PersonnalisationCommande ajouter(
            @RequestBody PersonnalisationCommande perso) {
        return service.ajouter(perso);
    }

    @DeleteMapping("/supprimer")
    @ResponseBody
    public String supprimer(@RequestParam Long id) {
        service.supprimer(id);
        return "OK";
    }

    @GetMapping("/liste")
    @ResponseBody
    public List<PersonnalisationCommande> liste(@RequestParam Long idLigne) {
        return service.findByLigne(idLigne);
    }

    @GetMapping("/actions")
    @ResponseBody
    public List<ActionCommande> actions() {
        return service.findAllActions();
    }

    @GetMapping("/ingredients")
    @ResponseBody
    public List<Ingredient> ingredients() {
        return service.findAllIngredients();
    }

    @GetMapping("/recette")
    @ResponseBody
    public List<PersonnalisationService.RecetteIngredient> recette(
            @RequestParam Long idProduit) {
        return service.findIngredientsProduit(idProduit);
    }
}
