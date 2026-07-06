package com.mkalymlam.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SuiviSessionService {

    private final JdbcTemplate jdbcTemplate;

    public SuiviSessionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Double getChiffreAffaire(Long idSession) {
        String sql = """
            SELECT COALESCE(SUM("montantTotal"), 0)
            FROM "commande"
            WHERE "idSession" = ?
            """;
        return jdbcTemplate.queryForObject(sql, Double.class, idSession);
    }

    public List<Map<String, Object>> getCommandes(Long idSession) {
        String sql = """
            SELECT
                c."idCommande" AS "idCommande",
                c."montantTotal" AS "montantTotal",
                c."dateHeureCreation" AS "dateHeureCreation",
                tc."libelle" AS "typeCommande",
                sc."libelle" AS "statutCommande",
                u."prenom" || ' ' || u."nom" AS "vendeuse"
            FROM "commande" c
            LEFT JOIN "typeCommande" tc ON tc."idTypeCommande" = c."idTypeCommande"
            LEFT JOIN "statutCommande" sc ON sc."idStatutCommande" = c."idStatutCommande"
            LEFT JOIN "utilisateur" u ON u."idUtilisateur" = c."idVendeuse"
            WHERE c."idSession" = ?
            ORDER BY c."dateHeureCreation" DESC
            """;
        return jdbcTemplate.queryForList(sql, idSession);
    }

    public Integer getNombreVentesRealisees(Long idSession) {
        String sql = """
            SELECT COUNT(*)
            FROM "commande"
            WHERE "idSession" = ?
            """;
        return jdbcTemplate.queryForObject(sql, Integer.class, idSession);
    }

    public Double getTotalDepenses(Long idSession) {
        String sql = """
            SELECT COALESCE(SUM("montantDepense"), 0)
            FROM "depense"
            WHERE "idSession" = ?
            """;
        return jdbcTemplate.queryForObject(sql, Double.class, idSession);
    }

    public List<Map<String, Object>> getDepenses(Long idSession) {
        String sql = """
            SELECT
                d."idDepense" AS "idDepense",
                d."montantDepense" AS "montantDepense",
                d."raisonDetaillee" AS "raisonDetaillee",
                d."dateDepense" AS "dateDepense",
                td."libelle" AS "typeDepense",
                sv."libelle" AS "statutValidation"
            FROM "depense" d
            LEFT JOIN "typeDepense" td ON td."idTypeDepense" = d."idTypeDepense"
            LEFT JOIN "statutValidationAdmin" sv ON sv."idStatutValidationAdmin" = d."idStatutValidationAdmin"
            WHERE d."idSession" = ?
            ORDER BY d."dateDepense" DESC
            """;
        return jdbcTemplate.queryForList(sql, idSession);
    }
}
