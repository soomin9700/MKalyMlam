-- Mettre à jour les lignes avec prixUnitaireFacture = NULL
UPDATE "ligneCommande" l
SET "prixUnitaireFacture" = p."prixBase"
FROM "produit" p
WHERE l."idProduit" = p."idProduit"
AND (l."prixUnitaireFacture" IS NULL OR l."prixUnitaireFacture" = 0);
