package com.mkalymlam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

// import com.mkalymlam.entity.Ingredient;
// import com.mkalymlam.repository.IngredientRepository;


@Service
@Transactional
public class StatistiqueService {

    private final JdbcTemplate jdbcTemplate;

    public StatistiqueService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Map<String,Object>> executeStatQuery(String sql,Object... params){

    return jdbcTemplate.queryForList(sql,params);

}

    public Double getChiffreAffaireGlobal() {

        String sql = """
            SELECT COALESCE(SUM("montantTotal"),0)
            FROM "commande"
            """;

        return jdbcTemplate.queryForObject(sql, Double.class);
    }


    public Double getChiffreAffaireByIdSession(Long idSession) {

        String sql = """
            SELECT COALESCE(SUM("montantTotal"),0)
            FROM "commande" where commande.idSession = ?
            """;

        return jdbcTemplate.queryForObject(sql, Double.class, idSession);
    }

    public Double getChiffreAffaireByZone(String zone) {
        String sql="""
            SELECT
            COALESCE(SUM("chiffreAffaireTotal"),0)
            FROM "view_Itineraire_SessionTruck_Depense"
            WHERE "nomZone"=?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                Double.class,
                zone
        );
    }


    public Double getBeneficeByZone(String zone) {
        String sql="""
            SELECT
            COALESCE(SUM("chiffreAffaireTotal"),0)
            -
            COALESCE(SUM("montantDepenseTotal"),0)
            FROM "view_Itineraire_SessionTruck_Depense"
            WHERE "nomZone"=?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                Double.class,
                zone
        );
    }


    public Double getChiffreAffaireByIdSessionHebdomadaire(Long idSession) {

        String sql = """
            SELECT COALESCE(SUM("montantTotal"),0)
            FROM "commande"
            WHERE "idSession" = ?
            AND "dateHeureCreation"
            >= CURRENT_DATE - INTERVAL '7 days';
            """;

        return jdbcTemplate.queryForObject(sql, Double.class, idSession);
    }


    public Double getChiffreAffaireByIdSessionDates(Long idSession, LocalDateTime date1, LocalDateTime date2) {

        String sql = """
            SELECT COALESCE(SUM("montantTotal"),0)
            FROM "commande" where commande.idSession = ?
            and "dateHeureCreation" >= ? and "dateHeureCreation" <= ?
            """;

        return jdbcTemplate.queryForObject(sql, Double.class, idSession, date1, date2);
    }

    public Double getChiffreAffaireByIdSessionMensuel(Long idSession) {

        String sql = """
            SELECT * from view_CA_Depense_Benefice_Mensuel
            where "idSession" = ?
            """;

        return jdbcTemplate.queryForObject(sql, Double.class, idSession);
    }

    public Double getBeneficeByIdItineraire(Long idItineraire){

        String sql="""
            SELECT
            COALESCE(SUM("chiffreAffaireTotal"),0)
            -
            COALESCE(SUM("montantDepenseTotal"),0)
            FROM "view_Itineraire_SessionTruck_Depense"
            WHERE "idItineraire"=?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                Double.class,
                idItineraire
        );
    }

    public Double getBeneficeTotal(){

        String sql="""
            SELECT COALESCE(SUM("montantDepense"),0)
            FROM "depense"
            """;

        Double depense=jdbcTemplate.queryForObject(sql,Double.class);

        return getChiffreAffaireGlobal()-depense;
    }


    public List<Map<String, Object>> getDonneesGraphique() {
        String sql = """
                    SELECT
                        COALESCE(rev."date", dep."date") AS "periode",
                        COALESCE(rev."chiffreAffaire", 0) AS "chiffreAffaire",
                        COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
                    FROM (
                        SELECT "dateSession" AS "date", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                        FROM "sessionTruck"
                        GROUP BY "dateSession"
                    ) rev
                    FULL JOIN (
                        SELECT "dateDepense" AS "date", SUM("montantDepense") AS "montantDepense"
                        FROM "depense"
                        GROUP BY "dateDepense"
                    ) dep ON dep."date" = rev."date"
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getChiffreAffaireParJour() {
        String sql = """
                    SELECT "dateSession" AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                    FROM "sessionTruck"
                    GROUP BY "dateSession"
                    ORDER BY "dateSession"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getChiffreAffaireParSemaine() {
        String sql = """
                    SELECT date_trunc('week', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                    FROM "sessionTruck"
                    GROUP BY date_trunc('week', "dateSession")
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getChiffreAffaireParMois() {
        String sql = """
                    SELECT date_trunc('month', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                    FROM "sessionTruck"
                    GROUP BY date_trunc('month', "dateSession")
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getBeneficeParJour() {
        String sql = """
                    SELECT COALESCE(rev."date", dep."date") AS "periode",
                        COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
                    FROM (
                        SELECT "dateSession" AS "date", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                        FROM "sessionTruck"
                        GROUP BY "dateSession"
                    ) rev
                    FULL JOIN (
                        SELECT "dateDepense" AS "date", SUM("montantDepense") AS "montantDepense"
                        FROM "depense"
                        GROUP BY "dateDepense"
                    ) dep ON dep."date" = rev."date"
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getBeneficeParSemaine() {
        String sql = """
                    SELECT COALESCE(rev."periode", dep."periode") AS "periode",
                        COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
                    FROM (
                        SELECT date_trunc('week', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                        FROM "sessionTruck"
                        GROUP BY date_trunc('week', "dateSession")
                    ) rev
                    FULL JOIN (
                        SELECT date_trunc('week', "dateDepense")::date AS "periode", SUM("montantDepense") AS "montantDepense"
                        FROM "depense"
                        GROUP BY date_trunc('week', "dateDepense")
                    ) dep ON dep."periode" = rev."periode"
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getBeneficeParMois() {
        String sql = """
                    SELECT COALESCE(rev."periode", dep."periode") AS "periode",
                        COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
                    FROM (
                        SELECT date_trunc('month', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
                        FROM "sessionTruck"
                        GROUP BY date_trunc('month', "dateSession")
                    ) rev
                    FULL JOIN (
                        SELECT date_trunc('month', "dateDepense")::date AS "periode", SUM("montantDepense") AS "montantDepense"
                        FROM "depense"
                        GROUP BY date_trunc('month', "dateDepense")
                    ) dep ON dep."periode" = rev."periode"
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql);
    }

    public List<Map<String, Object>> getBeneficeParItineraireParJour(Long idItineraire) {
        String sql = """
                    SELECT st."dateSession" AS "periode",
                        COALESCE(SUM(st."chiffreAffaireTotal"), 0) - COALESCE(SUM(d."montantDepense"), 0) AS "benefice"
                    FROM "sessionTruck" st
                    LEFT JOIN "depense" d ON d."idSession" = st."idSession"
                    WHERE st."idItineraire" = ?
                    GROUP BY st."dateSession"
                    ORDER BY st."dateSession"
                """;
        return executeStatQuery(sql, idItineraire);
    }

    public List<Map<String, Object>> getBeneficeParItineraireParSemaine(Long idItineraire) {
        String sql = """
                    SELECT date_trunc('week', st."dateSession")::date AS "periode",
                        COALESCE(SUM(st."chiffreAffaireTotal"), 0) - COALESCE(SUM(d."montantDepense"), 0) AS "benefice"
                    FROM "sessionTruck" st
                    LEFT JOIN "depense" d ON d."idSession" = st."idSession"
                    WHERE st."idItineraire" = ?
                    GROUP BY date_trunc('week', st."dateSession")
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql, idItineraire);
    }

    public List<Map<String, Object>> getBeneficeParItineraireParMois(Long idItineraire) {
        String sql = """
                    SELECT date_trunc('month', st."dateSession")::date AS "periode",
                        COALESCE(SUM(st."chiffreAffaireTotal"), 0) - COALESCE(SUM(d."montantDepense"), 0) AS "benefice"
                    FROM "sessionTruck" st
                    LEFT JOIN "depense" d ON d."idSession" = st."idSession"
                    WHERE st."idItineraire" = ?
                    GROUP BY date_trunc('month', st."dateSession")
                    ORDER BY "periode"
                """;
        return executeStatQuery(sql, idItineraire);
    }


}