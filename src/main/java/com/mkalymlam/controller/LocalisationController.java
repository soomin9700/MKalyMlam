package com.mkalymlam.controller;

import com.mkalymlam.entity.Itineraire;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.entity.SessionTruckPosition;
import com.mkalymlam.entity.Truck;
import com.mkalymlam.service.ItineraireService;
import com.mkalymlam.service.SessionTruckPositionService;
import com.mkalymlam.service.SessionTruckService;
import com.mkalymlam.service.TruckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/localisation")
public class LocalisationController {

    private final ItineraireService itineraireService;
    private final SessionTruckService sessionTruckService;
    private final SessionTruckPositionService positionService;
    private final TruckService truckService;

    public LocalisationController(ItineraireService itineraireService,
                                  SessionTruckService sessionTruckService,
                                  SessionTruckPositionService positionService,
                                  TruckService truckService) {
        this.itineraireService = itineraireService;
        this.sessionTruckService = sessionTruckService;
        this.positionService = positionService;
        this.truckService = truckService;
    }

    @GetMapping("/form")
    public String formLocalisation(Model model) {
        List<SessionTruck> sessionsOuvertes = sessionTruckService.findSessionsDuJour();
        model.addAttribute("sessions", sessionsOuvertes);
        
        model.addAttribute("itineraires", itineraireService.findAll());
        
        return "localisation/form";
    }

    @PostMapping("/publier")
    public String publierPosition(@RequestParam("idSession") Long idSession,
                                  @RequestParam("idItineraire") Long idItineraire,
                                  @RequestParam(value = "heureArrivee", required = false) String heureArrivee,
                                  RedirectAttributes redirectAttributes) {
        try {
            LocalTime heure = heureArrivee != null && !heureArrivee.isEmpty() 
                ? LocalTime.parse(heureArrivee) 
                : LocalTime.now();
                
            positionService.publierPosition(idSession, idItineraire, heure);
            redirectAttributes.addFlashAttribute("success", "Position publiée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la publication : " + e.getMessage());
        }
        return "redirect:/localisation/list";
    }

    @GetMapping("/list")
    public String listLocalisation(Model model) {
        List<SessionTruckPosition> positions = positionService.getLatestPositionsForToday();
        model.addAttribute("positions", positions);
        return "localisation/list";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SessionTruckPosition position = positionService.findById(id);
        model.addAttribute("position", position);
        
        model.addAttribute("itineraires", itineraireService.findAll());
        
        return "localisation/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePosition(@PathVariable Long id,
                                 @RequestParam("idItineraire") Long idItineraire,
                                 @RequestParam(value = "heureArrivee", required = false) String heureArrivee,
                                 RedirectAttributes redirectAttributes) {
        try {
            LocalTime heure = heureArrivee != null && !heureArrivee.isEmpty() 
                ? LocalTime.parse(heureArrivee) 
                : LocalTime.now();
                
            positionService.updatePosition(id, idItineraire, heure);
            redirectAttributes.addFlashAttribute("success", "Position mise à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
        }
        return "redirect:/localisation/list";
    }

    @PostMapping("/delete/{id}")
    public String deletePosition(@PathVariable Long id, 
                                 RedirectAttributes redirectAttributes) {
        try {
            positionService.deletePosition(id);
            redirectAttributes.addFlashAttribute("success", "Position supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/localisation/list";
    }

}