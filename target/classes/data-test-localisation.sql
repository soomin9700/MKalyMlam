




-- VERIFICATIONS

-- 1. Vérifier les sessions ouvertes (doit afficher 6 sessions)
SELECT * FROM "sessionTruck" WHERE "idStatutSession" = 1;

-- 2. Vérifier les positions publiées (doit afficher 13 positions)
SELECT 
    t."immatriculation",
    i."nomZone",
    i."lieuExact",
    sp."heureArrivee",
    sp."datePublication",
    ss."libelle" as statut_session
FROM "sessionTruckPosition" sp
JOIN "sessionTruck" st ON sp."idSession" = st."idSession"
JOIN "truck" t ON st."idTruck" = t."idTruck"
JOIN "itineraire" i ON sp."idItineraire" = i."idItineraire"
JOIN "statutSession" ss ON st."idStatutSession" = ss."idStatutSession"
ORDER BY sp."datePublication" DESC, sp."heureArrivee";

-- 3. Vérifier les trucks disponibles pour les sessions
SELECT 
    t."idTruck",
    t."immatriculation",
    sd."libelle" as statut,
    COUNT(st."idSession") as nb_sessions
FROM "truck" t
LEFT JOIN "statutDisponibilite" sd ON t."idStatutDisponibilite" = sd."idStatutDisponibilite"
LEFT JOIN "sessionTruck" st ON t."idTruck" = st."idTruck" AND st."idStatutSession" = 1
GROUP BY t."idTruck", t."immatriculation", sd."libelle"
ORDER BY t."idTruck";

-- 4. Vérifier les itinéraires disponibles
SELECT * FROM "itineraire" ORDER BY "idItineraire";