CREATE OR REPLACE VIEW "view_Itineraire_SessionTruck_Depense" AS
SELECT
    i."idItineraire",
    i."nomZone",

    s."idSession",
    s."idTruck",
    s."dateSession",
    s."fondDeCaisseOuverture",
    s."fondDeCaisseCloture",
    s."chiffreAffaireTotal",

    COALESCE(d."montantDepenseTotal",0) AS "montantDepenseTotal"

FROM "itineraire" i

INNER JOIN "sessionTruck" s
    ON i."idItineraire" = s."idItineraire"

LEFT JOIN (
    SELECT
        "idSession",
        SUM("montantDepense") AS "montantDepenseTotal"
    FROM "depense"
    GROUP BY "idSession"
) d
    ON s."idSession" = d."idSession";