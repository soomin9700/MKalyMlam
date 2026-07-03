-- Supprimer la table si elle existe
DROP TABLE IF EXISTS "ingredient" CASCADE;

-- Créer la table avec les colonnes camelCase
CREATE TABLE "ingredient" (
    "idIngredient" SERIAL PRIMARY KEY,
    "nomIngredient" VARCHAR(100) NOT NULL,
    "seuilAlerteQuantite" NUMERIC(10, 2) NOT NULL,
    "uniteMesure" VARCHAR(20) NOT NULL
);

-- Insérer des données de test
INSERT INTO "ingredient" ("nomIngredient", "seuilAlerteQuantite", "uniteMesure") VALUES
('Farine de blé', 5.0, 'kg'),
('Sucre cristallisé', 3.0, 'kg'),
('Sel fin', 1.0, 'kg'),
('Beurre doux', 2.5, 'kg'),
('Œufs frais', 12.0, 'unité');


ALTER TABLE "ingredient" ADD COLUMN prix DOUBLE PRECISION DEFAULT 0;
UPDATE "ingredient" SET prix = 1500 WHERE "nomIngredient" = 'Farine de blé';
UPDATE "ingredient" SET prix = 2000 WHERE "nomIngredient" = 'Sucre cristallisé';
UPDATE "ingredient" SET prix = 500  WHERE "nomIngredient" = 'Sel fin';
UPDATE "ingredient" SET prix = 4000 WHERE "nomIngredient" = 'Beurre doux';
UPDATE "ingredient" SET prix = 800  WHERE "nomIngredient" = 'Œufs frais';
