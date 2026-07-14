package com.mkalymlam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkalymlam.entity.Itineraire;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.entity.SessionTruckPosition;
import com.mkalymlam.service.ItineraireService;
import com.mkalymlam.service.ProduitService;
import com.mkalymlam.service.SessionTruckPositionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/front")
public class FrontController {

    private final ItineraireService itineraireService;
    private final ProduitService produitService;
    private final ObjectMapper objectMapper;
    private final SessionTruckPositionService positionTruckService;

    public FrontController(ItineraireService itineraireService, 
                           ProduitService produitService,
                           ObjectMapper objectMapper, 
                           SessionTruckPositionService positionTruckService) {
        this.itineraireService = itineraireService;
        this.produitService = produitService;
        this.objectMapper = objectMapper;
        this.positionTruckService = positionTruckService;
    }

    @GetMapping
    public String formLocalisation(Model model) throws JsonProcessingException {
        // 1. Récupérer toutes les positions des trucks du jour (dernières positions)
        List<SessionTruckPosition> truckPositions = positionTruckService.getLatestPositionsForToday();
        
        // 2. Récupérer tous les itinéraires
        List<Itineraire> itineraires = itineraireService.findAll();
        
        // 3. Récupérer tous les produits pour le menu complet
        List<Produit> produits = produitService.findAll();
        
        // 4. Récupérer les produits vedettes (limités à 3)
        List<Produit> featuredProduits = produitService.findAllLimit(3);

        // Construction des vues
        List<Map<String, Object>> itinerairesView = buildItinerairesView(itineraires);
        List<Map<String, Object>> produitsView = buildProduitsView(produits);
        List<Map<String, Object>> featuredProductsView = buildProduitsView(featuredProduits);

        // Ajout des attributs au modèle
        model.addAttribute("positions", truckPositions);
        model.addAttribute("itineraires", itinerairesView);
        model.addAttribute("produits", produitsView);
        model.addAttribute("featuredProducts", featuredProductsView);
        model.addAttribute("menuItemsJson", objectMapper.writeValueAsString(produitsView));
        model.addAttribute("itinerairesJson", objectMapper.writeValueAsString(itinerairesView));

        return "front/index";
    }

    private List<Map<String, Object>> buildProduitsView(List<Produit> produits) {
        if (produits == null || produits.isEmpty()) {
            return defaultProduits();
        }

        List<Map<String, Object>> items = new ArrayList<>();
        for (Produit produit : produits) {
            Map<String, Object> item = new LinkedHashMap<>();
            String nomProduit = produit.getNomProduit() != null ? produit.getNomProduit() : "Produit spécial";
            String category = inferCategory(nomProduit);
            Double rawPrice = produit.getPrixBase() != null ? produit.getPrixBase() : 0.0;

            item.put("id", produit.getIdProduit());
            item.put("name", nomProduit);
            item.put("category", category);
            item.put("price", Math.round(rawPrice));
            item.put("priceLabel", formatPrice(rawPrice));
            item.put("tag", Boolean.TRUE.equals(produit.getEstNouveau()) ? "new" : "popular");
            item.put("desc", buildDescription(nomProduit));
            item.put("img", resolveImage(nomProduit, category));
            items.add(item);
        }

        return items;
    }

    private List<Map<String, Object>> buildItinerairesView(List<Itineraire> itineraires) {
        if (itineraires == null || itineraires.isEmpty()) {
            return defaultItineraires();
        }

        List<Map<String, Object>> items = new ArrayList<>();
        int index = 0;
        for (Itineraire itineraire : itineraires) {
            index++;
            String nomZone = itineraire.getNomZone() != null ? itineraire.getNomZone() : "Zone de service";
            String lieuExact = itineraire.getLieuExact() != null ? itineraire.getLieuExact() : "À définir";
            Time debut = itineraire.getHeureDebutPrevue();
            Time fin = itineraire.getHeureFinPrevue();
            String schedule = formatSchedule(debut, fin, itineraire.getJourSemaine());
            String truckName = "Truck " + index;

            List<Map<String, Object>> stops = new ArrayList<>();
            if (debut != null && fin != null) {
                LocalTime start = debut.toLocalTime();
                LocalTime end = fin.toLocalTime();
                LocalTime mid = start.plusMinutes(Duration.between(start, end).toMinutes() / 2);
                stops.add(Map.of("time", formatTime(debut), "label", "Point de départ - " + nomZone, "icon", "flag-checkered"));
                stops.add(Map.of("time", formatTime(Time.valueOf(mid)), "label", "Arrêt intermédiaire - " + lieuExact, "icon", "location-dot"));
                stops.add(Map.of("time", formatTime(fin), "label", "Destination finale - " + nomZone, "icon", "flag"));
            } else {
                stops.add(Map.of("time", "08h00", "label", "Point de départ - " + nomZone, "icon", "flag-checkered"));
                stops.add(Map.of("time", "09h00", "label", "Arrêt intermédiaire - " + lieuExact, "icon", "location-dot"));
                stops.add(Map.of("time", "10h00", "label", "Destination finale - " + nomZone, "icon", "flag"));
            }

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", itineraire.getId());
            item.put("truckName", truckName);
            item.put("nomZone", nomZone);
            item.put("lieuExact", lieuExact);
            item.put("schedule", schedule);
            item.put("status", "Ouvert & En Place");
            item.put("stops", stops);
            items.add(item);
        }

        return items;
    }

    private String inferCategory(String nomProduit) {
        String normalized = nomProduit.toLowerCase();
        if (normalized.contains("burger")) {
            return "burgers";
        }
        if (normalized.contains("taco")) {
            return "tacos";
        }
        if (normalized.contains("frites") || normalized.contains("tofu") || 
            normalized.contains("nem") || normalized.contains("samoussa")) {
            return "accompagnements";
        }
        if (normalized.contains("hot") || normalized.contains("dog")) {
            return "classics";
        }
        return "classics";
    }

    private String resolveImage(String nomProduit, String category) {
        String normalized = nomProduit.toLowerCase();
        if (normalized.contains("burger")) {
            return "images/burger.jpg";
        }
        if (normalized.contains("taco")) {
            return "images/tacos.jpg";
        }
        if (normalized.contains("hot") || normalized.contains("dog")) {
            return "images/hotdog.jpg";
        }
        if ("accompagnements".equals(category)) {
            return "images/home-hero.jpg";
        }
        return "images/logo.jpg";
    }

    private String buildDescription(String nomProduit) {
        String normalized = nomProduit.toLowerCase();
        if (normalized.contains("burger")) {
            return "Recette gourmande préparée à la minute avec des ingrédients frais.";
        }
        if (normalized.contains("taco")) {
            return "Tacos croustillants servis avec une sauce maison et des garnitures savoureuses.";
        }
        if (normalized.contains("hot") || normalized.contains("dog")) {
            return "Hot dog gourmand avec une sauce onctueuse et des toppings généreux.";
        }
        if (normalized.contains("tteokbokki")) {
            return "Spécialité coréenne aux saveurs épicées et authentiques.";
        }
        if (normalized.contains("samoussa") || normalized.contains("nem")) {
            return "Croustillant et savoureux, parfait en entrée ou en snack.";
        }
        return "Spécialité de la street food, prête à faire revenir les gourmands.";
    }

    private String formatPrice(Double price) {
        if (price == null) {
            return "0";
        }
        return String.format("%,d", Math.round(price));
    }

    private String formatSchedule(Time debut, Time fin, String jourSemaine) {
        StringBuilder schedule = new StringBuilder();
        if (jourSemaine != null && !jourSemaine.isBlank()) {
            schedule.append(jourSemaine).append(" · ");
        }
        if (debut != null) {
            schedule.append(formatTime(debut));
        } else {
            schedule.append("08h00");
        }
        schedule.append(" - ");
        if (fin != null) {
            schedule.append(formatTime(fin));
        } else {
            schedule.append("10h00");
        }
        return schedule.toString();
    }

    private String formatTime(Time time) {
        if (time == null) {
            return "08h00";
        }
        LocalTime localTime = time.toLocalTime();
        return String.format("%02dh%02d", localTime.getHour(), localTime.getMinute());
    }

    private List<Map<String, Object>> defaultProduits() {
        List<Map<String, Object>> items = new ArrayList<>();
        items.add(Map.of(
            "id", 1, 
            "name", "Le Boss Burger", 
            "category", "burgers", 
            "price", 12500, 
            "priceLabel", "12 500", 
            "tag", "popular", 
            "desc", "Burger gourmand maison avec une sauce fumée.", 
            "img", "images/burger.jpg"
        ));
        items.add(Map.of(
            "id", 2, 
            "name", "Le Crazy Tacos", 
            "category", "tacos", 
            "price", 10000, 
            "priceLabel", "10 000", 
            "tag", "new", 
            "desc", "Tacos croustillants garnis de frites et sauce fromagère.", 
            "img", "images/tacos.jpg"
        ));
        items.add(Map.of(
            "id", 3, 
            "name", "L'Extreme Hot Dog", 
            "category", "classics", 
            "price", 8500, 
            "priceLabel", "8 500", 
            "tag", "popular", 
            "desc", "Hot dog grillé avec oignons frits et cheddar fondant.", 
            "img", "images/hotdog.jpg"
        ));
        return items;
    }

    private List<Map<String, Object>> defaultItineraires() {
        List<Map<String, Object>> items = new ArrayList<>();
        items.add(Map.of(
            "id", 1, 
            "truckName", "Truck Alpha", 
            "nomZone", "Analakely", 
            "lieuExact", "Devant la gare", 
            "schedule", "Lundi · 08h00 - 10h00", 
            "status", "Ouvert & En Place", 
            "stops", List.of(
                Map.of("time", "08h00", "label", "Point de départ - Garage Central", "icon", "flag-checkered"),
                Map.of("time", "09h00", "label", "Arrêt intermédiaire - Analakely", "icon", "location-dot"),
                Map.of("time", "10h00", "label", "Destination finale - Zone de déchargement", "icon", "flag")
            )
        ));
        items.add(Map.of(
            "id", 2, 
            "truckName", "Truck Beta", 
            "nomZone", "Ankorondrano", 
            "lieuExact", "Près du fleuve", 
            "schedule", "Mardi · 08h30 - 10h30", 
            "status", "Ouvert & En Place", 
            "stops", List.of(
                Map.of("time", "08h30", "label", "Point de départ - Garage Nord", "icon", "flag-checkered"),
                Map.of("time", "09h15", "label", "Arrêt intermédiaire - Ankorondrano", "icon", "location-dot"),
                Map.of("time", "10h30", "label", "Destination finale - Parking Smart Shop", "icon", "flag")
            )
        ));
        return items;
    }
}