package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Notification;
import com.mkalymlam.entity.TypeNotification;
import com.mkalymlam.service.NotificationService;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    // ---------- Vue Thymeleaf (liste) ----------
    @GetMapping
    public String list(Model model) {
        model.addAttribute("notifications", service.findAll());
        return "notification/list";
    }

    // ---------- 6.3.4 Consulter (REST) ----------
    @GetMapping("/liste")
    @ResponseBody
    public List<Notification> liste() {
        return service.findAll();
    }

    @GetMapping("/dernieres")
    @ResponseBody
    public List<Notification> dernieres() {
        return service.findDernieres();
    }

    @GetMapping("/produit/{idProduit}")
    @ResponseBody
    public List<Notification> parProduit(@PathVariable Long idProduit) {
        return service.findByProduit(idProduit);
    }

    @GetMapping("/session/{sessionId}")
    @ResponseBody
    public List<Notification> parSession(@PathVariable Long sessionId) {
        return service.findBySession(sessionId);
    }

    @GetMapping("/type/{type}")
    @ResponseBody
    public List<Notification> parType(@PathVariable TypeNotification type) {
        return service.findByType(type);
    }

    
    @PostMapping("/publier")
    @ResponseBody
    public Notification publier(
            @RequestParam String titre,
            @RequestParam String contenu,
            @RequestParam(required = false) Integer auteurId) {
        return service.publier(titre, contenu, auteurId);
    }

    
    @PostMapping("/annoncer-produit")
    @ResponseBody
    public Notification annoncerNouveauProduit(
            @RequestParam Long idProduit,
            @RequestParam String titre,
            @RequestParam String contenu,
            @RequestParam(required = false) Integer auteurId) {
        return service.annoncerNouveauProduit(idProduit, titre, contenu, auteurId);
    }

 
    @PostMapping("/annoncer-point-de-vente")
    @ResponseBody
    public Notification annoncerPointDeVente(
            @RequestParam Long sessionId,
            @RequestParam String contenu,
            @RequestParam(required = false) Integer auteurId) {
        return service.annoncerPointDeVente(sessionId, contenu, auteurId);
    }

   
    @PostMapping("/{id}/edit")
    @ResponseBody
    public Notification modifier(
            @PathVariable Long id,
            @RequestParam String titre,
            @RequestParam String contenu,
            @RequestParam(required = false) Boolean publie) {
        return service.modifier(id, titre, contenu, publie);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/notifications";
    }
}