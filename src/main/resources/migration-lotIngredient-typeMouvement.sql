-- ============================================================================
-- Migration : synchronise la base avec les entités JPA du module Stocks/Lots.
--
-- Contexte : la base foodTruckDb avait été créée avant l'ajout du type de
-- mouvement. Il manquait :
--   1) la table "typeMouvement" (utilisée par l'entité TypeMouvement et par
--      le formulaire /lot/ingredients/new),
--   2) la colonne "idTypeMouvement" dans "lotIngredient" (mappée par
--      l'entité LotIngredient via @ManyToOne).
--
-- Ce script est idempotent (IF NOT EXISTS) et sans perte de données.
-- ============================================================================

-- 1) Table des types de mouvement + données de référence
CREATE TABLE IF NOT EXISTS "typeMouvement" (
    "idTypeMouvement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

INSERT INTO "typeMouvement" ("libelle")
SELECT v.libelle
FROM (VALUES ('ENTREE'), ('SORTIE')) AS v(libelle)
WHERE NOT EXISTS (
    SELECT 1 FROM "typeMouvement" tm WHERE tm."libelle" = v.libelle
);

-- 2) Colonne idTypeMouvement dans lotIngredient (nullable : l'association
--    JPA est optionnelle et la table peut déjà contenir des lignes).
ALTER TABLE "lotIngredient" ADD COLUMN IF NOT EXISTS "idTypeMouvement" INT;

-- 3) Clé étrangère (ajoutée seulement si absente)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'lotIngredient_idTypeMouvement_fkey'
    ) THEN
        ALTER TABLE "lotIngredient"
            ADD CONSTRAINT "lotIngredient_idTypeMouvement_fkey"
            FOREIGN KEY ("idTypeMouvement")
            REFERENCES "typeMouvement"("idTypeMouvement");
    END IF;
END $$;

-- 4) Table des mouvements de lot (utilisée par le calcul de stock / alertes)
CREATE TABLE IF NOT EXISTS "mouvementLotIngredient" (
    "idMouvementLot" SERIAL PRIMARY KEY,
    "idLot" INT NOT NULL,
    "idTypeMouvement" INT NOT NULL,
    "quantite" NUMERIC(10, 2) NOT NULL CHECK ("quantite" > 0),
    "dateMouvement" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("idLot") REFERENCES "lotIngredient"("idLot"),
    FOREIGN KEY ("idTypeMouvement") REFERENCES "typeMouvement"("idTypeMouvement")
);
