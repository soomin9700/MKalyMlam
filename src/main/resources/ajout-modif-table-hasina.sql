-- nouvelle table 
CREATE TABLE "mouvementEquipement" (
    "idMouvementEquipement" SERIAL PRIMARY KEY,
    "idTypeMouvement" INT NOT NULL,
    "idEquipement" INT NOT NULL,
    "quantite" NUMERIC(15,2) NOT NULL,
    "dateMouvement" DATE NOT NULL,

    FOREIGN KEY ("idTypeMouvement")
        REFERENCES "typeMouvement"("idTypeMouvement"),

    FOREIGN KEY ("idEquipement")
        REFERENCES "equipement"("idEquipement")
);

CREATE TABLE "typeMouvement" (
    "idTypeMouvement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

INSERT INTO "typeMouvement" ("libelle")
VALUES
('ENTREE'),
('SORTIE');


-- modif table 
CREATE TABLE "equipement" (
    "idEquipement" SERIAL PRIMARY KEY,
    "nomEquipement" VARCHAR(100) NOT NULL,
    "idTypeEquipement" INT NOT NULL,
    "idMethodeComptable" INT NOT NULL,
    "prixUnitaire" NUMERIC(15,2) NOT NULL,
    "quantiteMin" NUMERIC(15,2) NOT NULL,

    FOREIGN KEY ("idTypeEquipement") REFERENCES "typeEquipement"("idTypeEquipement"),
    FOREIGN KEY ("idMethodeComptable") REFERENCES "methodeComptable"("idMethodeComptable")
);


-- ajout de colonne typeMouvement dans la table lotIngredient
-- suppression de la colonne quanttieRestante
ALTER TABLE "lotIngredient" ALTER COLUMN "quantiteRestante" DROP NOT NULL;
ALTER TABLE "lotIngredient" DROP COLUMN "quantiteRestante";
ALTER TABLE "lotIngredient" ADD COLUMN "idTypeMouvement" INT;

CREATE TABLE "lotIngredient" (
    "idLot" SERIAL PRIMARY KEY,
    "idIngredient" INT NOT NULL,
    "dateReception" DATE NOT NULL,
    "datePeremption" DATE NOT NULL,
    "quantiteInitiale" NUMERIC(10, 2) NOT NULL,
    "prixAchatUnitaire" NUMERIC(10, 2) NOT NULL,
    "idTypeMouvement" INT NOT NULL,
    FOREIGN KEY ("idTypeMouvement") REFERENCES "typeMouvement"("idTypeMouvement"),
    FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient")
);

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
