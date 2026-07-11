CREATE TABLE "retourClient" (
    "idRetour" SERIAL PRIMARY KEY,
    "idTypeRetour" INT NOT NULL,
    "noteSur10" INT CHECK ("noteSur10" >= 0 AND "noteSur10" <= 10),
    "contenuTexte" TEXT NOT NULL,
    "idClassificationSentiment" INT NOT NULL,
    "estPopulaire" BOOLEAN DEFAULT FALSE,
    "dateSoumission" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("idTypeRetour") REFERENCES "typeRetour"("idTypeRetour"),
    FOREIGN KEY ("idClassificationSentiment") REFERENCES "classificationSentiment"("idClassificationSentiment")
);

CREATE TABLE "actionAmelioration" (
    "idAction" SERIAL PRIMARY KEY,
    "idRetourOrigine" INT NOT NULL,
    "idAuteurAdmin" INT NOT NULL,
    "instructionEmployes" TEXT NOT NULL,
    "idStatutDemandeAchat" INT NOT NULL,
    FOREIGN KEY ("idRetourOrigine") REFERENCES "retourClient"("idRetour"),
    FOREIGN KEY ("idAuteurAdmin") REFERENCES "utilisateur"("idUtilisateur"),
    FOREIGN KEY ("idStatutDemandeAchat") REFERENCES "statutDemandeAchat"("idStatutDemandeAchat")
);

CREATE TABLE "historiqueStatutCommande" (
    "idHistorique" SERIAL PRIMARY KEY,
    "idCommande" INT NOT NULL,
    "ancienStatut" VARCHAR(50),
    "nouveauStatut" VARCHAR(50) NOT NULL,
    "dateChangement" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("idCommande") REFERENCES "commande"("idCommande")
);

CREATE TABLE "historiqueConsommation" (
    "idConsommation" SERIAL PRIMARY KEY,
    "idCommande" INT NOT NULL,
    "idIngredient" INT NOT NULL,
    "quantiteConsommee" NUMERIC(10, 2) NOT NULL,
    "dateConsommation" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "idSession" INT NOT NULL,
    FOREIGN KEY ("idCommande") REFERENCES "commande"("idCommande"),
    FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient"),
    FOREIGN KEY ("idSession") REFERENCES "sessionTruck"("idSession")
);

CREATE TABLE "notificationPlateforme" (
    "idNotification" SERIAL PRIMARY KEY,
    "idTypeNotification" INT NOT NULL,
    "titre" VARCHAR(150) NOT NULL,
    "message" TEXT NOT NULL,
    "idProduitLie" INT,
    "idSessionLiee" INT,
    "dateHeureEnvoi" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("idTypeNotification") REFERENCES "typeNotification"("idTypeNotification"),
    FOREIGN KEY ("idProduitLie") REFERENCES "produit"("idProduit"),
    FOREIGN KEY ("idSessionLiee") REFERENCES "sessionTruck"("idSession")
);