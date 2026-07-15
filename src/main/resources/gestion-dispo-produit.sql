
CREATE TABLE "disponibiliteProduit" (
    "id" SERIAL PRIMARY KEY,
    "idProduit" INT NOT NULL,
    "dateModification" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "estDisponible" BOOLEAN NOT NULL,
    FOREIGN KEY ("idProduit") REFERENCES "produit"("idProduit")
);

-- Vue pour simuler la colonne (SANS ALTER TABLE)

CREATE VIEW "produit_avec_disponibilite" AS
SELECT 
    p.*,
    COALESCE(
        (SELECT d."estDisponible" 
         FROM "disponibiliteProduit" d 
         WHERE d."idProduit" = p."idProduit" 
         ORDER BY d."dateModification" DESC 
         LIMIT 1),
        true
    ) as "estDisponible"
FROM "produit" p;


-- Insérer une entrée pour chaque produit existant
-- Utiliser la date de création du produit comme date de début de disponibilité
INSERT INTO "disponibiliteProduit" ("idProduit", "estDisponible", "dateModification")
SELECT 
    "idProduit",
    true,
    "dateCreation"  -- Date de création du produit
FROM "produit";