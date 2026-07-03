-- Drop si la table existe déjà
DROP INDEX IF EXISTS "idx_personnalisationLigne";
DROP TABLE IF EXISTS "personnalisationCommande" CASCADE;
DROP TYPE IF EXISTS "actionPersonnalisation";

-- Type ENUM pour l'action
CREATE TYPE "actionPersonnalisation" AS ENUM ('AJOUTER', 'RETIRER');

-- Table personnalisationCommande
CREATE TABLE "personnalisationCommande" (
    "idPersonnalisation" BIGSERIAL PRIMARY KEY,
    "idLigne" BIGINT NOT NULL,
    "idIngredient" INTEGER NOT NULL,
    "idActionCommande" INTEGER NOT NULL,
    "quantiteAjustee" NUMERIC(10,2) NOT NULL,

    CONSTRAINT "fk_personnalisationLigne"
        FOREIGN KEY ("idLigne") REFERENCES "ligneCommande"("idLigne") ON DELETE CASCADE,

    CONSTRAINT "fk_personnalisationIngredient"
        FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient") ON DELETE RESTRICT,

    CONSTRAINT "fk_personnalisationAction"
        FOREIGN KEY ("idActionCommande") REFERENCES "actionCommande"("idActionCommande") ON DELETE RESTRICT
);

-- Index pour accélérer les recherches par ligne de commande
CREATE INDEX "idx_personnalisationLigne" ON "personnalisationCommande"("idLigne");

-- Exemple de requête
SELECT c."idCommande", l."idLigne", i."nomIngredient", p."quantiteAjustee"
FROM "commande" c
JOIN "ligneCommande" l ON c."idCommande" = l."idCommande"
JOIN "personnalisationCommande" p ON l."idLigne" = p."idLigne"
JOIN "ingredient" i ON p."idIngredient" = i."idIngredient"
WHERE c."idCommande" = 45;
