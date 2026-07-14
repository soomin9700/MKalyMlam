package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.entity.NotificationPlateforme;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.entity.TypeNotification;
import com.mkalymlam.service.ItineraireService;
import com.mkalymlam.service.NotificationPlateformeService;
import com.mkalymlam.service.ProduitService;
import com.mkalymlam.service.SessionTruckService;
import com.mkalymlam.service.TypeNotificationService;

@Controller
@RequestMapping("/localisation")
public class LocalisationController {

    private final ItineraireService itineraire;
    private final NotificationPlateformeService notificationService;
    private final TypeNotificationService typeNotificationService;
    private final ProduitService produitService;
    private final SessionTruckService sessionTruckService;

    public LocalisationController(
            ItineraireService itineraire,
            NotificationPlateformeService notificationService,
            TypeNotificationService typeNotificationService,
            ProduitService produitService,
            SessionTruckService sessionTruckService) {
        this.itineraire = itineraire;
        this.notificationService = notificationService;
        this.typeNotificationService = typeNotificationService;
        this.produitService = produitService;
        this.sessionTruckService = sessionTruckService;
    }

    @GetMapping("/form")
    public String formLocalisation(Model model) {
        model.addAttribute("itineraire", itineraire.findAll());
        List<TypeNotification> types = typeNotificationService.findAll();
        List<Produit> produits = produitService.findAll();
        List<SessionTruck> sessions = sessionTruckService.findAll();
        List<NotificationPlateforme> notifications = notificationService.findAll();

        model.addAttribute("typesNotification", types);
        model.addAttribute("produits", produits);
        model.addAttribute("sessions", sessions);
        model.addAttribute("notifications", notifications);
        model.addAttribute("notification", new NotificationPlateforme());
        model.addAttribute("actionUrl", "/localisation/publier");
        return "localisation/form";
    }

    @PostMapping("/publier")
    public String publier(
            @RequestParam Long typeNotification,
            @RequestParam String titre,
            @RequestParam String message,
            @RequestParam(required = false) Long produitLie,
            @RequestParam(required = false) Long sessionLiee) {

        NotificationPlateforme notification = new NotificationPlateforme();
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setTypeNotification(typeNotificationService.findById(typeNotification));
        if (produitLie != null) {
            notification.setProduitLie(produitService.getById(produitLie));
        }
        if (sessionLiee != null) {
            notification.setSessionLiee(sessionTruckService.find(sessionLiee));
        }

        notificationService.save(notification);
        return "redirect:/localisation/form";
    }

    @GetMapping("/list")
    public String listLocalisation(Model model) {
        return "localisation/list";
    }
}
