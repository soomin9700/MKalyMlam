package com.mkalymlam.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class StatistiqueRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Double getChiffreAffaireGlobal() {
        return singleDoubleResult("""
                SELECT COALESCE(SUM(\"montantTotal\"), 0)
                FROM \"commande\"
                """);
    }

    public Double getBeneficeTotal() {
        return singleDoubleResult("""
                SELECT COALESCE(SUM(\"montantDepense\"), 0)
                FROM \"depense\"
                """);
    }

    public Double getBeneficeByIdItineraire(Long idItineraire) {
        return singleDoubleResult("""
                SELECT
                    COALESCE(SUM(\"chiffreAffaireTotal\"),0)
                    -
                    COALESCE(SUM(\"montantDepenseTotal\"),0)
                FROM \"view_Itineraire_SessionTruck_Depense\"
                WHERE \"idItineraire\" = :idItineraire
                """, "idItineraire", idItineraire);
    }

    public List<Object[]> getDonneesGraphique() {
        return executeQuery("""
                SELECT
                    COALESCE(rev.\"date\", dep.\"date\") AS \"periode\",
                    COALESCE(rev.\"chiffreAffaire\", 0) AS \"chiffreAffaire\",
                    COALESCE(rev.\"chiffreAffaire\", 0) - COALESCE(dep.\"montantDepense\", 0) AS \"benefice\"
                FROM (
                    SELECT \"dateSession\" AS \"date\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                    FROM \"sessionTruck\"
                    GROUP BY \"dateSession\"
                ) rev
                FULL JOIN (
                    SELECT \"dateDepense\" AS \"date\", SUM(\"montantDepense\") AS \"montantDepense\"
                    FROM \"depense\"
                    GROUP BY \"dateDepense\"
                ) dep ON dep.\"date\" = rev.\"date\"
                ORDER BY \"periode\"
                """);
    }

    public List<Object[]> getChiffreAffaireParJour() {
        return executeQuery("""
                SELECT \"dateSession\" AS \"periode\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                FROM \"sessionTruck\"
                GROUP BY \"dateSession\"
                ORDER BY \"dateSession\"
                """);
    }

    public List<Object[]> getChiffreAffaireParSemaine() {
        return executeQuery(
                """
                        SELECT date_trunc('week', \"dateSession\")::date AS \"periode\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                        FROM \"sessionTruck\"
                        GROUP BY date_trunc('week', \"dateSession\")
                        ORDER BY \"periode\"
                        """);
    }

    public List<Object[]> getChiffreAffaireParMois() {
        return executeQuery(
                """
                        SELECT date_trunc('month', \"dateSession\")::date AS \"periode\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                        FROM \"sessionTruck\"
                        GROUP BY date_trunc('month', \"dateSession\")
                        ORDER BY \"periode\"
                        """);
    }

    public List<Object[]> getBeneficeParJour() {
        return executeQuery("""
                SELECT COALESCE(rev.\"date\", dep.\"date\") AS \"periode\",
                    COALESCE(rev.\"chiffreAffaire\", 0) - COALESCE(dep.\"montantDepense\", 0) AS \"benefice\"
                FROM (
                    SELECT \"dateSession\" AS \"date\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                    FROM \"sessionTruck\"
                    GROUP BY \"dateSession\"
                ) rev
                FULL JOIN (
                    SELECT \"dateDepense\" AS \"date\", SUM(\"montantDepense\") AS \"montantDepense\"
                    FROM \"depense\"
                    GROUP BY \"dateDepense\"
                ) dep ON dep.\"date\" = rev.\"date\"
                ORDER BY \"periode\"
                """);
    }

    public List<Object[]> getBeneficeParSemaine() {
        return executeQuery(
                """
                        SELECT COALESCE(rev.\"periode\", dep.\"periode\") AS \"periode\",
                            COALESCE(rev.\"chiffreAffaire\", 0) - COALESCE(dep.\"montantDepense\", 0) AS \"benefice\"
                        FROM (
                            SELECT date_trunc('week', \"dateSession\")::date AS \"periode\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                            FROM \"sessionTruck\"
                            GROUP BY date_trunc('week', \"dateSession\")
                        ) rev
                        FULL JOIN (
                            SELECT date_trunc('week', \"dateDepense\")::date AS \"periode\", SUM(\"montantDepense\") AS \"montantDepense\"
                            FROM \"depense\"
                            GROUP BY date_trunc('week', \"dateDepense\")
                        ) dep ON dep.\"periode\" = rev.\"periode\"
                        ORDER BY \"periode\"
                        """);
    }

    public List<Object[]> getBeneficeParMois() {
        return executeQuery(
                """
                        SELECT COALESCE(rev.\"periode\", dep.\"periode\") AS \"periode\",
                            COALESCE(rev.\"chiffreAffaire\", 0) - COALESCE(dep.\"montantDepense\", 0) AS \"benefice\"
                        FROM (
                            SELECT date_trunc('month', \"dateSession\")::date AS \"periode\", SUM(\"chiffreAffaireTotal\") AS \"chiffreAffaire\"
                            FROM \"sessionTruck\"
                            GROUP BY date_trunc('month', \"dateSession\")
                        ) rev
                        FULL JOIN (
                            SELECT date_trunc('month', \"dateDepense\")::date AS \"periode\", SUM(\"montantDepense\") AS \"montantDepense\"
                            FROM \"depense\"
                            GROUP BY date_trunc('month', \"dateDepense\")
                        ) dep ON dep.\"periode\" = rev.\"periode\"
                        ORDER BY \"periode\"
                        """);
    }

    public List<Object[]> getBeneficeParItineraireParJour(Long idItineraire) {
        return executeQuery(
                """
                        SELECT st.\"dateSession\" AS \"periode\",
                            COALESCE(SUM(st.\"chiffreAffaireTotal\"), 0) - COALESCE(SUM(d.\"montantDepense\"), 0) AS \"benefice\"
                        FROM \"sessionTruck\" st
                        LEFT JOIN \"depense\" d ON d.\"idSession\" = st.\"idSession\"
                        WHERE st.\"idItineraire\" = :idItineraire
                        GROUP BY st.\"dateSession\"
                        ORDER BY st.\"dateSession\"
                        """,
                "idItineraire", idItineraire);
    }

    public List<Object[]> getBeneficeParItineraireParSemaine(Long idItineraire) {
        return executeQuery(
                """
                        SELECT date_trunc('week', st.\"dateSession\")::date AS \"periode\",
                            COALESCE(SUM(st.\"chiffreAffaireTotal\"), 0) - COALESCE(SUM(d.\"montantDepense\"), 0) AS \"benefice\"
                        FROM \"sessionTruck\" st
                        LEFT JOIN \"depense\" d ON d.\"idSession\" = st.\"idSession\"
                        WHERE st.\"idItineraire\" = :idItineraire
                        GROUP BY date_trunc('week', st.\"dateSession\")
                        ORDER BY \"periode\"
                        """,
                "idItineraire", idItineraire);
    }

    public List<Object[]> getBeneficeParItineraireParMois(Long idItineraire) {
        return executeQuery(
                """
                        SELECT date_trunc('month', st.\"dateSession\")::date AS \"periode\",
                            COALESCE(SUM(st.\"chiffreAffaireTotal\"), 0) - COALESCE(SUM(d.\"montantDepense\"), 0) AS \"benefice\"
                        FROM \"sessionTruck\" st
                        LEFT JOIN \"depense\" d ON d.\"idSession\" = st.\"idSession\"
                        WHERE st.\"idItineraire\" = :idItineraire
                        GROUP BY date_trunc('month', st.\"dateSession\")
                        ORDER BY \"periode\"
                        """,
                "idItineraire", idItineraire);
    }

    private Double singleDoubleResult(String sql, Object... params) {
        Query query = entityManager.createNativeQuery(sql);
        applyParameters(query, params);
        Object value = query.getSingleResult();
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return value == null ? 0.0 : Double.parseDouble(value.toString());
    }

    private List<Object[]> executeQuery(String sql, Object... params) {
        Query query = entityManager.createNativeQuery(sql);
        applyParameters(query, params);
        List<?> results = query.getResultList();
        List<Object[]> rows = new ArrayList<>();
        for (Object result : results) {
            if (result instanceof Object[] row) {
                rows.add(row);
            } else {
                rows.add(new Object[] { result });
            }
        }
        return rows;
    }

    private void applyParameters(Query query, Object... params) {
        for (int i = 0; i < params.length; i += 2) {
            if (i + 1 < params.length) {
                query.setParameter(String.valueOf(params[i]), params[i + 1]);
            }
        }
    }
}
