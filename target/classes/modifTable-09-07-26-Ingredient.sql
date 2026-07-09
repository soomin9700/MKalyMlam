CREATE TABLE "ingredient" (
    "idIngredient" SERIAL PRIMARY KEY,
    "nomIngredient" VARCHAR(100) NOT NULL,
    "seuilAlerteQuantite" NUMERIC(10,2) NOT NULL,
    "uniteMesure" VARCHAR(20) NOT NULL,
    "actif" BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE "lotIngredient" (
    "idLot" SERIAL PRIMARY KEY,
    "idIngredient" INT NOT NULL,
    "dateReception" DATE NOT NULL,
    "datePeremption" DATE NOT NULL,
    "quantiteInitiale" NUMERIC(10,2) NOT NULL,
    "prixAchatUnitaire" NUMERIC(10,2) NOT NULL,

    FOREIGN KEY ("idIngredient")
        REFERENCES "ingredient"("idIngredient")
);
CREATE TABLE "typeMouvement" (
    "idTypeMouvement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO "typeMouvement"(libelle)
VALUES
('ENTREE'),
('SORTIE');
CREATE TABLE "mouvementLotIngredient" (
    "idMouvementLot" SERIAL PRIMARY KEY,

    "idLot" INT NOT NULL,

    "idTypeMouvement" INT NOT NULL,

    "quantite" NUMERIC(10,2) NOT NULL CHECK ("quantite" > 0),

    "dateMouvement" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY ("idLot")
        REFERENCES "lotIngredient"("idLot"),

    FOREIGN KEY ("idTypeMouvement")
        REFERENCES "typeMouvement"("idTypeMouvement")
);