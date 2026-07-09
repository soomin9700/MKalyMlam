package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.entity.Depense;
import com.mkalymlam.service.DepenseService;
import com.mkalymlam.service.SessionTruckService;

@Controller
@RequestMapping("/session/{idSession}/depenses")
public class DepenseController {

    private final DepenseService depenseService;
    private final SessionTruckService sessionTruckService;

    public DepenseController(DepenseService depenseService,
                             SessionTruckService sessionTruckService) {
        this.depenseService = depenseService;
        this.sessionTruckService = sessionTruckService;
    }

    @GetMapping
    public String liste(@PathVariable Long idSession,
                        @RequestParam(required = false) Long idTypeDepense,
                        @RequestParam(required = false) Long idStatut,
                        Model model) {
        var session = sessionTruckService.find(idSession);
        List<Depense> depenses = depenseService.findBySession(idSession);

        if (idTypeDepense != null) {
            depenses = depenses.stream()
                    .filter(d -> d.getTypeDepense().getId().equals(idTypeDepense))
                    .toList();
        }
        if (idStatut != null) {
            depenses = depenses.stream()
                    .filter(d -> d.getStatutValidationAdmin().getId().equals(idStatut))
                    .toList();
        }

        model.addAttribute("session", session);
        model.addAttribute("depenses", depenses);
        model.addAttribute("types", depenseService.getAllTypes());
        model.addAttribute("statuts", depenseService.getAllStatuts());
        model.addAttribute("selectedType", idTypeDepense);
        model.addAttribute("selectedStatut", idStatut);
        return "depense/list";
    }

    @GetMapping("/ajouter")
    public String formAjout(@PathVariable Long idSession, Model model) {
        var session = sessionTruckService.find(idSession);
        model.addAttribute("session", session);
        model.addAttribute("types", depenseService.getAllTypes());
        return "depense/form";
    }

    @PostMapping("/ajouter")
    public String ajouter(@PathVariable Long idSession,
                          @RequestParam Long idTypeDepense,
                          @RequestParam Double montantDepense,
                          @RequestParam String raisonDetaillee,
                          RedirectAttributes redirectAttributes) {
        try {
            depenseService.ajouter(idSession, idTypeDepense, montantDepense, raisonDetaillee);
            redirectAttributes.addFlashAttribute("success", "Depense ajoutee avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/session/" + idSession + "/depenses";
    }

    @GetMapping("/{idDepense}/modifier")
    public String formModifier(@PathVariable Long idSession,
                               @PathVariable Long idDepense,
                               Model model) {
        Depense depense = depenseService.getById(idDepense);
        if (depense == null || !"EN_ATTENTE".equals(depense.getStatutValidationAdmin().getLibelle())) {
            return "redirect:/session/" + idSession + "/depenses";
        }
        model.addAttribute("depense", depense);
        model.addAttribute("session", depense.getSession());
        model.addAttribute("types", depenseService.getAllTypes());
        return "depense/edit";
    }

    @PostMapping("/{idDepense}/modifier")
    public String modifier(@PathVariable Long idSession,
                           @PathVariable Long idDepense,
                           @RequestParam Long idTypeDepense,
                           @RequestParam Double montantDepense,
                           @RequestParam String raisonDetaillee,
                           RedirectAttributes redirectAttributes) {
        try {
            depenseService.modifier(idDepense, idTypeDepense, montantDepense, raisonDetaillee);
            redirectAttributes.addFlashAttribute("success", "Depense modifiee avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/session/" + idSession + "/depenses";
    }

    @PostMapping("/{idDepense}/supprimer")
    public String supprimer(@PathVariable Long idSession,
                            @PathVariable Long idDepense,
                            RedirectAttributes redirectAttributes) {
        try {
            depenseService.supprimer(idDepense);
            redirectAttributes.addFlashAttribute("success", "Depense supprimee avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/session/" + idSession + "/depenses";
    }
}
