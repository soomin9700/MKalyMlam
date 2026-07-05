package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.dto.ApprovisionnementForm;
import com.mkalymlam.entity.Approvisionnement;
import com.mkalymlam.service.ApprovisionnementService;

@Controller
@RequestMapping("/approvisionnements")
public class ApprovisionnementController {

    private final ApprovisionnementService approvisionnementService;

    public ApprovisionnementController(ApprovisionnementService approvisionnementService) {
        this.approvisionnementService = approvisionnementService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("besoins", approvisionnementService.calculerBesoins());
        model.addAttribute("form", new ApprovisionnementForm());
        model.addAttribute("historiques", approvisionnementService.historique());
        return "approvisionnement/index";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@ModelAttribute("form") ApprovisionnementForm form,
                              RedirectAttributes redirectAttributes) {
        Approvisionnement approvisionnement = approvisionnementService.enregistrer(form);
        redirectAttributes.addFlashAttribute("successMessage",
                "Approvisionnement enregistre avec un cout estime de "
                        + approvisionnement.getCoutTotalEstime());
        return "redirect:/approvisionnements";
    }

    @GetMapping("/{idApprovisionnement}")
    public String detail(@PathVariable Long idApprovisionnement, Model model) {
        model.addAttribute("approvisionnement", approvisionnementService.findById(idApprovisionnement));
        model.addAttribute("details", approvisionnementService.details(idApprovisionnement));
        return "approvisionnement/detail";
    }
}
