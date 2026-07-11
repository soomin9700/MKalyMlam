package com.mkalymlam.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/commandes/export")
public class CommandeExportController {

    @PersistenceContext
    private EntityManager entityManager;

    // Libellés statiques (acceptent null)
    private String typeLibelle(Integer id) {
        if (id == null) return "?";
        return switch (id) {
            case 1 -> "Sur place";
            case 2 -> "À emporter";
            case 3 -> "En ligne";
            default -> "?";
        };
    }

    private String statutLibelle(Integer id) {
        if (id == null) return "?";
        return switch (id) {
            case 1 -> "En attente";
            case 2 -> "Préparation";
            case 3 -> "Prête";
            case 4 -> "Livrée";
            case 5 -> "Annulée";
            default -> "?";
        };
    }

    private String tarifLibelle(Integer id) {
        if (id == null) return "?";
        return switch (id) {
            case 1 -> "Normale";
            case 2 -> "Heure supp.";
            default -> "?";
        };
    }

    @GetMapping("/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {
        String sql = """
            SELECT "idCommande", "idSession", "idVendeuse", "idTypeCommande",
                   "dateHeureCreation", "montantTotal", "idStatutCommande", "idTypeTarification"
            FROM "commande"
            ORDER BY "idCommande"
        """;
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"commandes.csv\"");
        PrintWriter writer = response.getWriter();
        writer.println("ID,Session,Vendeuse,Type,Date,Montant,Statut,Tarification");

        for (Object[] row : rows) {
            String id = (row[0] != null) ? row[0].toString() : "";
            String session = (row[1] != null) ? row[1].toString() : "";
            String vendeuse = (row[2] != null) ? row[2].toString() : "";
            String type = (row[3] != null) ? typeLibelle(((Number) row[3]).intValue()) : "?";
            String date = (row[4] != null) ? row[4].toString() : "";
            String montant = (row[5] != null) ? String.format("%.2f", ((Number) row[5]).doubleValue()) : "0.00";
            String statut = (row[6] != null) ? statutLibelle(((Number) row[6]).intValue()) : "?";
            String tarif = (row[7] != null) ? tarifLibelle(((Number) row[7]).intValue()) : "?";

            writer.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                    id, session, vendeuse, type, date, montant, statut, tarif);
        }
        writer.flush();
    }

    @GetMapping("/pdf")
    public String printPage(Model model) {
        String sql = """
            SELECT "idCommande", "idSession", "idVendeuse", "idTypeCommande",
                   "dateHeureCreation", "montantTotal", "idStatutCommande", "idTypeTarification"
            FROM "commande"
            ORDER BY "idCommande"
        """;
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();

        List<Map<String, Object>> commandes = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", (row[0] != null) ? row[0].toString() : "");
            map.put("session", (row[1] != null) ? row[1].toString() : "");
            map.put("vendeuse", (row[2] != null) ? row[2].toString() : "");
            map.put("type", (row[3] != null) ? typeLibelle(((Number) row[3]).intValue()) : "?");
            map.put("date", (row[4] != null) ? row[4].toString() : "");
            map.put("montant", (row[5] != null) ? ((Number) row[5]).doubleValue() : 0.0);
            map.put("statut", (row[6] != null) ? statutLibelle(((Number) row[6]).intValue()) : "?");
            map.put("tarif", (row[7] != null) ? tarifLibelle(((Number) row[7]).intValue()) : "?");
            commandes.add(map);
        }
        model.addAttribute("commandes", commandes);
        return "commande/print";
    }
}