package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.service.DepenseService;

@Controller
@RequestMapping("/admin/depenses")
public class AdminDepenseController {

    private final DepenseService depenseService;

    public AdminDepenseController(DepenseService depenseService) {
        this.depenseService = depenseService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("depenses", depenseService.getEnAttente());
        return "admin/depenses";
    }

    @PostMapping("/{id}/valider")
    public String valider(@PathVariable Long id, RedirectAttributes ra) {
        try {
            depenseService.valider(id);
            ra.addFlashAttribute("success", "Depense #" + id + " validee.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/depenses";
    }

    @GetMapping("/{id}/refuser")
    public String formRefus(@PathVariable Long id, Model model) {
        model.addAttribute("depense", depenseService.getById(id));
        return "admin/refuser";
    }

    @PostMapping("/{id}/refuser")
    public String refuser(@PathVariable Long id,
                          @RequestParam(required = false) String commentaire,
                          RedirectAttributes ra) {
        try {
            depenseService.refuser(id, commentaire);
            ra.addFlashAttribute("success", "Depense #" + id + " refusee.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/depenses";
    }
}
