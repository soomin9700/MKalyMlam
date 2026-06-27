// package com.mkalymlam.service;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// // import jakarta.persistence.EntityManager;
// // import jakarta.persistence.PersistenceContext;
// import connexion.Connexion;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.ResultSetMetaData;
// import java.util.*;

// @Service
// @Transactional
// public class StatistiqueService {

//     public Double getChiffreAffaireGlobal() {
//         String sql = """
//                     SELECT COALESCE(SUM("montantTotal"), 0) AS chiffre_affaire
//                     FROM "commande"
//                 """;
//         try (
//                 Connection connection = Connexion.getconnexion().getConnection();
//                 PreparedStatement statement = connection.prepareStatement(sql);
//                 ResultSet rs = statement.executeQuery()) {

//             if (rs.next()) {
//                 return rs.getDouble("chiffre_affaire");
//             }

//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//         return 0.0;
//     }

//     public Double getBeneficeTotal() {
//         String sql = """
//                     SELECT COALESCE(SUM("montantDepense"), 0) AS total_depense
//                     FROM "depense"
//                 """;
//         Double depenseTotale = 0.0;
//         try (
//                 Connection connection = Connexion.getconnexion().getConnection();
//                 PreparedStatement statement = connection.prepareStatement(sql);
//                 ResultSet rs = statement.executeQuery()) {

//             if (rs.next()) {
//                 depenseTotale = rs.getDouble("total_depense");
//             }

//         } catch (Exception e) {
//             throw new RuntimeException("Erreur lors du calcul des dépenses totales", e);
//         }
//         Double chiffreAffaireGlobal = getChiffreAffaireGlobal();
//         Double beneficeTotal = chiffreAffaireGlobal - depenseTotale;
//         return beneficeTotal;
//     }

//     public Double getBeneficeByIdItineraire(Long idItineraire) {
//         String sql = """
//                     SELECT
//                         COALESCE(SUM("chiffreAffaireTotal"),0)
//                         -
//                         COALESCE(SUM("montantDepenseTotal"),0)
//                         AS benefice
//                     FROM "view_Itineraire_SessionTruck_Depense"
//                     WHERE "idItineraire" = ?
//                 """;

//         try (
//                 Connection connection = Connexion.getconnexion().getConnection();
//                 PreparedStatement statement = connection.prepareStatement(sql)) {

//             statement.setLong(1, idItineraire);

//             try (ResultSet rs = statement.executeQuery()) {

//                 if (rs.next()) {
//                     return rs.getDouble("benefice");
//                 }
//             }

//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }

//         return 0.0;
//     }

//     public List<Map<String, Object>> getDonneesGraphique() {
//         String sql = """
//                     SELECT
//                         COALESCE(rev."date", dep."date") AS "periode",
//                         COALESCE(rev."chiffreAffaire", 0) AS "chiffreAffaire",
//                         COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
//                     FROM (
//                         SELECT "dateSession" AS "date", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                         FROM "sessionTruck"
//                         GROUP BY "dateSession"
//                     ) rev
//                     FULL JOIN (
//                         SELECT "dateDepense" AS "date", SUM("montantDepense") AS "montantDepense"
//                         FROM "depense"
//                         GROUP BY "dateDepense"
//                     ) dep ON dep."date" = rev."date"
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getChiffreAffaireParJour() {
//         String sql = """
//                     SELECT "dateSession" AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                     FROM "sessionTruck"
//                     GROUP BY "dateSession"
//                     ORDER BY "dateSession"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getChiffreAffaireParSemaine() {
//         String sql = """
//                     SELECT date_trunc('week', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                     FROM "sessionTruck"
//                     GROUP BY date_trunc('week', "dateSession")
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getChiffreAffaireParMois() {
//         String sql = """
//                     SELECT date_trunc('month', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                     FROM "sessionTruck"
//                     GROUP BY date_trunc('month', "dateSession")
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getBeneficeParJour() {
//         String sql = """
//                     SELECT COALESCE(rev."date", dep."date") AS "periode",
//                         COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
//                     FROM (
//                         SELECT "dateSession" AS "date", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                         FROM "sessionTruck"
//                         GROUP BY "dateSession"
//                     ) rev
//                     FULL JOIN (
//                         SELECT "dateDepense" AS "date", SUM("montantDepense") AS "montantDepense"
//                         FROM "depense"
//                         GROUP BY "dateDepense"
//                     ) dep ON dep."date" = rev."date"
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getBeneficeParSemaine() {
//         String sql = """
//                     SELECT COALESCE(rev."periode", dep."periode") AS "periode",
//                         COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
//                     FROM (
//                         SELECT date_trunc('week', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                         FROM "sessionTruck"
//                         GROUP BY date_trunc('week', "dateSession")
//                     ) rev
//                     FULL JOIN (
//                         SELECT date_trunc('week', "dateDepense")::date AS "periode", SUM("montantDepense") AS "montantDepense"
//                         FROM "depense"
//                         GROUP BY date_trunc('week', "dateDepense")
//                     ) dep ON dep."periode" = rev."periode"
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getBeneficeParMois() {
//         String sql = """
//                     SELECT COALESCE(rev."periode", dep."periode") AS "periode",
//                         COALESCE(rev."chiffreAffaire", 0) - COALESCE(dep."montantDepense", 0) AS "benefice"
//                     FROM (
//                         SELECT date_trunc('month', "dateSession")::date AS "periode", SUM("chiffreAffaireTotal") AS "chiffreAffaire"
//                         FROM "sessionTruck"
//                         GROUP BY date_trunc('month', "dateSession")
//                     ) rev
//                     FULL JOIN (
//                         SELECT date_trunc('month', "dateDepense")::date AS "periode", SUM("montantDepense") AS "montantDepense"
//                         FROM "depense"
//                         GROUP BY date_trunc('month', "dateDepense")
//                     ) dep ON dep."periode" = rev."periode"
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql);
//     }

//     public List<Map<String, Object>> getBeneficeParItineraireParJour(Long idItineraire) {
//         String sql = """
//                     SELECT st."dateSession" AS "periode",
//                         COALESCE(SUM(st."chiffreAffaireTotal"), 0) - COALESCE(SUM(d."montantDepense"), 0) AS "benefice"
//                     FROM "sessionTruck" st
//                     LEFT JOIN "depense" d ON d."idSession" = st."idSession"
//                     WHERE st."idItineraire" = ?
//                     GROUP BY st."dateSession"
//                     ORDER BY st."dateSession"
//                 """;
//         return executeStatQuery(sql, idItineraire);
//     }

//     public List<Map<String, Object>> getBeneficeParItineraireParSemaine(Long idItineraire) {
//         String sql = """
//                     SELECT date_trunc('week', st."dateSession")::date AS "periode",
//                         COALESCE(SUM(st."chiffreAffaireTotal"), 0) - COALESCE(SUM(d."montantDepense"), 0) AS "benefice"
//                     FROM "sessionTruck" st
//                     LEFT JOIN "depense" d ON d."idSession" = st."idSession"
//                     WHERE st."idItineraire" = ?
//                     GROUP BY date_trunc('week', st."dateSession")
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql, idItineraire);
//     }

//     public List<Map<String, Object>> getBeneficeParItineraireParMois(Long idItineraire) {
//         String sql = """
//                     SELECT date_trunc('month', st."dateSession")::date AS "periode",
//                         COALESCE(SUM(st."chiffreAffaireTotal"), 0) - COALESCE(SUM(d."montantDepense"), 0) AS "benefice"
//                     FROM "sessionTruck" st
//                     LEFT JOIN "depense" d ON d."idSession" = st."idSession"
//                     WHERE st."idItineraire" = ?
//                     GROUP BY date_trunc('month', st."dateSession")
//                     ORDER BY "periode"
//                 """;
//         return executeStatQuery(sql, idItineraire);
//     }

//     private List<Map<String, Object>> executeStatQuery(String sql, Object... params) {
//         List<Map<String, Object>> results = new ArrayList<>();
//         try (
//                 Connection connection = Connexion.getconnexion().getConnection();
//                 PreparedStatement statement = connection.prepareStatement(sql)) {
//             for (int i = 0; i < params.length; i++) {
//                 statement.setObject(i + 1, params[i]);
//             }
//             try (ResultSet rs = statement.executeQuery()) {
//                 ResultSetMetaData meta = rs.getMetaData();
//                 while (rs.next()) {
//                     Map<String, Object> row = new HashMap<>();
//                     for (int i = 1; i <= meta.getColumnCount(); i++) {
//                         row.put(meta.getColumnLabel(i), rs.getObject(i));
//                     }
//                     results.add(row);
//                 }
//             }
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//         return results;
//     }
// }
