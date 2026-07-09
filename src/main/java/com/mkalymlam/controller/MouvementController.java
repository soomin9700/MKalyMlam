package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.service.LotIngredientService;

@Controller
@RequestMapping("/mouvements")
public class MouvementController {

    private final LotIngredientService lotIngredientService;

    public MouvementController(LotIngredientService lotIngredientService) {
        this.lotIngredientService = lotIngredientService;
    }

    @GetMapping("/sortie/{idLot}")
    public String formSortie(@PathVariable Long idLot, Model model) {
        var lot = lotIngredientService.getById(idLot);
        if (lot == null) {
            return "redirect:/lot/findAll";
        }
        model.addAttribute("lot", lot);
        model.addAttribute("quantiteRestante", lotIngredientService.calculQuantiteRestante(idLot));
        return "mouvement/form";
    }

    @PostMapping("/sortie/{idLot}")
    public String ajouterSortie(@PathVariable Long idLot,
                                 @RequestParam Double quantite) {
        try {
            lotIngredientService.ajouterSortie(idLot, quantite);
        } catch (IllegalArgumentException e) {
            return "redirect:/mouvements/sortie/" + idLot + "?error=" + e.getMessage();
        }
        return "redirect:/lot/findAll";
    }
}
