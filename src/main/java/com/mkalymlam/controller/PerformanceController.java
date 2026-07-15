package com.mkalymlam.controller;

import com.mkalymlam.dto.TopProduitDTO;
import com.mkalymlam.dto.VenteStatsDTO;
import com.mkalymlam.service.PerformanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }


    // Dashboard des performances (page HTML)


    @GetMapping
    public String dashboard(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            Model model) {

        // Statistiques générales
        VenteStatsDTO stats;
        if (dateDebut != null && dateFin != null) {
            stats = performanceService.getVenteStatsBetween(dateDebut, dateFin);
        } else {
            stats = performanceService.getVenteStats();
        }

        // Top 5 produits
        List<TopProduitDTO> topProduits;
        if (dateDebut != null && dateFin != null) {
            topProduits = performanceService.getTopProduitsBetween(dateDebut, dateFin);
        } else {
            topProduits = performanceService.getTopProduits();
        }

        // Données pour les graphiques
        List<Object[]> last7Days = performanceService.getLast7DaysStats();

        model.addAttribute("stats", stats);
        model.addAttribute("topProduits", topProduits);
        model.addAttribute("last7Days", last7Days);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);

        return "performance/dashboard";
    }


    // API REST pour les statistiques


    @GetMapping("/api/stats")
    @ResponseBody
    public VenteStatsDTO getStats(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin) {
        if (dateDebut != null && dateFin != null) {
            return performanceService.getVenteStatsBetween(dateDebut, dateFin);
        }
        return performanceService.getVenteStats();
    }

    @GetMapping("/api/top-produits")
    @ResponseBody
    public List<TopProduitDTO> getTopProduits(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin) {
        if (dateDebut != null && dateFin != null) {
            return performanceService.getTopProduitsBetween(dateDebut, dateFin);
        }
        return performanceService.getTopProduits();
    }

    @GetMapping("/api/popularite")
    @ResponseBody
    public Long getPopularite(
            @RequestParam Long idProduit,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin) {
        if (dateDebut != null && dateFin != null) {
            return performanceService.getPopulariteProduitBetween(idProduit, dateDebut, dateFin);
        }
        return performanceService.getPopulariteProduit(idProduit);
    }
}