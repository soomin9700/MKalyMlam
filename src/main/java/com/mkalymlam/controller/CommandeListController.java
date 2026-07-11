package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.entity.StatutCommande;
import com.mkalymlam.entity.TypeCommande;
import com.mkalymlam.repository.StatutCommandeRepository;
import com.mkalymlam.repository.TypeCommandeRepository;
import com.mkalymlam.service.VenteService;

@Controller
@RequestMapping("/commande")
public class CommandeListController {

    private final VenteService venteService;
    private final StatutCommandeRepository statutCommandeRepository;
    private final TypeCommandeRepository typeCommandeRepository;

    public CommandeListController(VenteService venteService,
                                  StatutCommandeRepository statutCommandeRepository,
                                  TypeCommandeRepository typeCommandeRepository) {
        this.venteService = venteService;
        this.statutCommandeRepository = statutCommandeRepository;
        this.typeCommandeRepository = typeCommandeRepository;
    }

    @GetMapping("/liste")
    public String getListe(@RequestParam(required = false) String statut,
                           @RequestParam(required = false) String type,
                           Model model) {

        model.addAttribute("commandes", venteService.listerCommandesFiltrees(statut, type));
        model.addAttribute("statuts", statutCommandeRepository.findAll());
        model.addAttribute("types", typeCommandeRepository.findAll());
        model.addAttribute("selectedStatut", statut);
        model.addAttribute("selectedType", type);

        return "commande/listeCommandes";
    }

    @PostMapping("/changerStatut")
    public String changerStatut(@RequestParam Long idCommande,
                                @RequestParam String statut,
                                RedirectAttributes redirectAttributes) {
        try {
            venteService.changerStatut(idCommande, statut);
            redirectAttributes.addFlashAttribute("success", "Statut mis à jour avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/commande/liste";
    }
}
