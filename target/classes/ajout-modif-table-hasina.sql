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