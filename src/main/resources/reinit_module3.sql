\c postgres;
drop database "foodTruckDb";

CREATE DATABASE "foodTruckDb";
\c "foodTruckDb";


-- Module 1
CREATE TABLE "role" (
    "idRole" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "typeConge" (
    "idTypeConge" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "statutValidation" (
    "idStatutValidation" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

-- Module 2
CREATE TABLE "statutDisponibilite" (
    "idStatutDisponibilite" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "statutSession" (
    "idStatutSession" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

-- Module 3
CREATE TABLE "typeEquipement" (
    "idTypeEquipement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "statutAlerte" (
    "idStatutAlerte" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "methodeComptable" (
    "idMethodeComptable" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "typeItem" (
    "idTypeItem" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

-- Module 4
CREATE TABLE "typeCommande" (
    "idTypeCommande" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "statutCommande" (
    "idStatutCommande" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "typeTarification" (
    "idTypeTarification" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "actionCommande" (
    "idActionCommande" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "modePaiement" (
    "idModePaiement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

-- Module 5
CREATE TABLE "typeDepense" (
    "idTypeDepense" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "statutValidationAdmin" (
    "idStatutValidationAdmin" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

-- Module 6
CREATE TABLE "typeRetour" (
    "idTypeRetour" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "classificationSentiment" (
    "idClassificationSentiment" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "statutDemandeAchat" (
    "idStatutDemandeAchat" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "typeNotification" (
    "idTypeNotification" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);


-- ==============================================================================
-- MODULE 1 : Utilisateurs, Ressources Humaines & Paie
-- ==============================================================================

CREATE TABLE "utilisateur" (
    "idUtilisateur" SERIAL PRIMARY KEY,
    "nom" VARCHAR(100) NOT NULL,
    "prenom" VARCHAR(100),
    "email" VARCHAR(150) UNIQUE NOT NULL,
    "motDePasse" VARCHAR(255) NOT NULL,
    "idRole" INT NOT NULL,
    "salaireBaseFixe" NUMERIC(12, 2),
    "statutActif" BOOLEAN DEFAULT TRUE,
    FOREIGN KEY ("idRole") REFERENCES "role"("idRole")
);

CREATE TABLE "absenceConge" (
    "idAbsence" SERIAL PRIMARY KEY,
    "idUtilisateur" INT NOT NULL,
    "idTypeConge" INT NOT NULL,
    "dateDebut" DATE NOT NULL,
    "dateFin" DATE NOT NULL,
    "idStatutValidation" INT NOT NULL,
    "deductionSalaireAppliquee" NUMERIC(10, 2) DEFAULT 0,
    "idRemplacant" INT,
    FOREIGN KEY ("idUtilisateur") REFERENCES "utilisateur"("idUtilisateur"),
    FOREIGN KEY ("idTypeConge") REFERENCES "typeConge"("idTypeConge"),
    FOREIGN KEY ("idStatutValidation") REFERENCES "statutValidation"("idStatutValidation"),
    FOREIGN KEY ("idRemplacant") REFERENCES "utilisateur"("idUtilisateur")
);

CREATE TABLE "fichePaie" (
    "idFiche" SERIAL PRIMARY KEY,
    "idUtilisateur" INT NOT NULL,
    "moisAnnee" VARCHAR(7) NOT NULL,
    "montantFixeBrut" NUMERIC(12, 2) NOT NULL,
    "totalCommissions" NUMERIC(10, 2) DEFAULT 0,
    "totalHeuresSupp" NUMERIC(10, 2) DEFAULT 0,
    "totalDeductionsAbsences" NUMERIC(10, 2) DEFAULT 0,
    "montantNetVerse" NUMERIC(12, 2) NOT NULL,
    "datePaiement" DATE,
    FOREIGN KEY ("idUtilisateur") REFERENCES "utilisateur"("idUtilisateur")
);

CREATE TABLE "historiqueSalaire" (
    "idHistorique" SERIAL PRIMARY KEY,
    "idUtilisateur" INT NOT NULL,
    "salaireBase" NUMERIC(12, 2) NOT NULL,
    "dateDebut" DATE NOT NULL,
    "dateFin" DATE,
    FOREIGN KEY ("idUtilisateur") REFERENCES "utilisateur"("idUtilisateur")
);


-- ==============================================================================
-- MODULE 2 : Planification, Sessions de Terrain & Véhicule
-- ==============================================================================

CREATE TABLE "truck" (
    "idTruck" SERIAL PRIMARY KEY,
    "immatriculation" VARCHAR(20) NOT NULL UNIQUE,
    "idStatutDisponibilite" INT NOT NULL,
    FOREIGN KEY ("idStatutDisponibilite") REFERENCES "statutDisponibilite"("idStatutDisponibilite")
);

CREATE TABLE "historiqueMaintenance" (
    "idHistoriqueMaintenance" SERIAL PRIMARY KEY,
    "idTruck" INT NOT NULL,
    "dateDebut" DATE NOT NULL,
    "dateFin" DATE,
    "description" TEXT,
    FOREIGN KEY ("idTruck") REFERENCES "truck"("idTruck")
);

CREATE TABLE "historiqueStatus" (
    "idHistoriqueStatus" SERIAL PRIMARY KEY,
    "idTruck" INT NOT NULL,
    "idStatutDisponibilite" INT NOT NULL,
    "dateChangement" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("idTruck") REFERENCES "truck"("idTruck"),
    FOREIGN KEY ("idStatutDisponibilite") REFERENCES "statutDisponibilite"("idStatutDisponibilite")
);

CREATE TABLE "itineraire" (
    "idItineraire" SERIAL PRIMARY KEY,
    "nomZone" VARCHAR(100) NOT NULL,
    "lieuExact" TEXT NOT NULL,
    "heureDebutPrevue" TIME NOT NULL,
    "heureFinPrevue" TIME NOT NULL,
    "jourSemaine" VARCHAR(20) NOT NULL
);

CREATE TABLE "sessionTruck" (
    "idSession" SERIAL PRIMARY KEY,
    "idTruck" INT NOT NULL,
    "idItineraire" INT NOT NULL,
    "dateSession" DATE NOT NULL,
    "fondDeCaisseOuverture" NUMERIC(10, 2),
    "fondDeCaisseCloture" NUMERIC(10, 2),
    "chiffreAffaireTotal" NUMERIC(12, 2) DEFAULT 0,
    "commissionTotaleEquipe" NUMERIC(10, 2) DEFAULT 0,
    "idStatutSession" INT NOT NULL,
    FOREIGN KEY ("idTruck") REFERENCES "truck"("idTruck"),
    FOREIGN KEY ("idItineraire") REFERENCES "itineraire"("idItineraire"),
    FOREIGN KEY ("idStatutSession") REFERENCES "statutSession"("idStatutSession")
);

CREATE TABLE "equipeSession" (
    "idEquipeSession" SERIAL PRIMARY KEY,
    "idSession" INT NOT NULL,
    "idUtilisateur" INT NOT NULL,
    "idRoleDuJour" INT NOT NULL,
    "salaireJournalierRemplacant" NUMERIC(10, 2),
    FOREIGN KEY ("idSession") REFERENCES "sessionTruck"("idSession"),
    FOREIGN KEY ("idUtilisateur") REFERENCES "utilisateur"("idUtilisateur"),
    FOREIGN KEY ("idRoleDuJour") REFERENCES "role"("idRole")
);

CREATE TABLE "demandeChangementItineraire" (
    "idDemande" SERIAL PRIMARY KEY,
    "idSession" INT NOT NULL,
    "idDemandeur" INT NOT NULL,
    "raison" TEXT NOT NULL,
    "idItinerairePropose" INT,
    "autreLieuPrecise" TEXT,
    "dateHeureDemande" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "idStatutValidation" INT NOT NULL,
    FOREIGN KEY ("idSession") REFERENCES "sessionTruck"("idSession"),
    FOREIGN KEY ("idDemandeur") REFERENCES "utilisateur"("idUtilisateur"),
    FOREIGN KEY ("idItinerairePropose") REFERENCES "itineraire"("idItineraire"),
    FOREIGN KEY ("idStatutValidation") REFERENCES "statutValidation"("idStatutValidation")
);


-- ==============================================================================
-- MODULE 3 : Stocks, Lots & Inventaires
-- ==============================================================================

CREATE TABLE "ingredient" (
    "idIngredient" SERIAL PRIMARY KEY,
    "nomIngredient" VARCHAR(100) NOT NULL,
    "seuilAlerteQuantite" NUMERIC(10, 2) NOT NULL,
    "uniteMesure" VARCHAR(20) NOT NULL,
    "actif" BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE "lotIngredient" (
    "idLot" SERIAL PRIMARY KEY,
    "idIngredient" INT NOT NULL,
    "dateReception" DATE NOT NULL,
    "datePeremption" DATE NOT NULL,
    "quantiteInitiale" NUMERIC(10, 2) NOT NULL,
    "prixAchatUnitaire" NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient")
);

-- modifs equipement et ingredients
CREATE TABLE "equipement" (
    "idEquipement" SERIAL PRIMARY KEY,
    "nomEquipement" VARCHAR(100) NOT NULL,
    "idTypeEquipement" INT NOT NULL,
    "idMethodeComptable" INT NOT NULL,
    "prixUnitaire" NUMERIC(15,2) NOT NULL,
    "quantiteMin" NUMERIC(15,2) NOT NULL,
    FOREIGN KEY ("idTypeEquipement")
        REFERENCES "typeEquipement"("idTypeEquipement"),
    FOREIGN KEY ("idMethodeComptable")
        REFERENCES "methodeComptable"("idMethodeComptable")
);

CREATE TABLE "typeMouvement" (
    "idTypeMouvement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

CREATE TABLE "mouvementLotIngredient" (
    "idmouvementLotIngredient" SERIAL PRIMARY KEY,
    "idTypeMouvement" INT NOT NULL,
    "idLot" INT NOT NULL,
    "quantite" NUMERIC(15,2) NOT NULL,
    "dateMouvement" DATE NOT NULL,
    FOREIGN KEY ("idTypeMouvement")
        REFERENCES "typeMouvement"("idTypeMouvement"),
    FOREIGN KEY ("idLot")
        REFERENCES "lotIngredient"("idLot")
);

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

CREATE TABLE "inventaireJournalier" (
    "idInventaire" SERIAL PRIMARY KEY,
    "idSession" INT NOT NULL,
    "dateInventaire" DATE NOT NULL,
    "idTypeItem" INT NOT NULL,
    "idItem" INT NOT NULL, -- ID polymorphique (Ingredient ou Equipement)
    "quantitePhysiqueConstatee" NUMERIC(10, 2) NOT NULL,
    "quantiteTheoriqueSysteme" NUMERIC(10, 2) NOT NULL,
    "ecartInventaire" NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY ("idSession") REFERENCES "sessionTruck"("idSession"),
    FOREIGN KEY ("idTypeItem") REFERENCES "typeItem"("idTypeItem")
);


-- ==============================================================================
-- MODULE 4 : Menu, Commandes Personnalisées & Factures
-- ==============================================================================

CREATE TABLE "produit" (
    "idProduit" SERIAL PRIMARY KEY,
    "nomProduit" VARCHAR(100) NOT NULL,
    "prixBase" NUMERIC(10, 2) NOT NULL,
    "estNouveau" BOOLEAN DEFAULT FALSE,
    "dateCreation" DATE DEFAULT CURRENT_DATE
);

CREATE TABLE "recetteDeBase" (

    "idProduit" INT NOT NULL,
    "idIngredient" INT NOT NULL,
    "quantiteRecette" NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY ("idProduit", "idIngredient"),
    FOREIGN KEY ("idProduit") REFERENCES "produit"("idProduit"),
    FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient")
);

CREATE TABLE "commande" (
    "idCommande" SERIAL PRIMARY KEY,
    "idSession" INT NOT NULL,
    "idVendeuse" INT,
    "idTypeCommande" INT NOT NULL,
    "dateHeureCreation" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "heureRecuperationPrevue" TIME,
    "lieuRecuperationPrevu" VARCHAR(150),
    "montantTotal" NUMERIC(12, 2) NOT NULL,
    "idStatutCommande" INT NOT NULL,
    "idTypeTarification" INT NOT NULL,
    FOREIGN KEY ("idSession") REFERENCES "sessionTruck"("idSession"),
    FOREIGN KEY ("idVendeuse") REFERENCES "utilisateur"("idUtilisateur"),
    FOREIGN KEY ("idTypeCommande") REFERENCES "typeCommande"("idTypeCommande"),
    FOREIGN KEY ("idStatutCommande") REFERENCES "statutCommande"("idStatutCommande"),
    FOREIGN KEY ("idTypeTarification") REFERENCES "typeTarification"("idTypeTarification")
);

CREATE TABLE "ligneCommande" (
    "idLigne" SERIAL PRIMARY KEY,
    "idCommande" INT NOT NULL,
    "idProduit" INT NOT NULL,
    "quantite" INT NOT NULL,
    "prixUnitaireFacture" NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY ("idCommande") REFERENCES "commande"("idCommande"),
    FOREIGN KEY ("idProduit") REFERENCES "produit"("idProduit")
);

CREATE TABLE "personnalisationCommande" (
    "idPersonnalisation" SERIAL PRIMARY KEY,
    "idLigne" INT NOT NULL,
    "idIngredient" INT NOT NULL,
    "idActionCommande" INT NOT NULL,
    "quantiteAjustee" NUMERIC(10, 2),
    FOREIGN KEY ("idLigne") REFERENCES "ligneCommande"("idLigne"),
    FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient"),
    FOREIGN KEY ("idActionCommande") REFERENCES "actionCommande"("idActionCommande")
);

CREATE TABLE "factureRecu" (
    "idFacture" SERIAL PRIMARY KEY,
    "idCommande" INT NOT NULL,
    "referenceFacture" VARCHAR(50) UNIQUE NOT NULL,
    "dateFacturation" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "idModePaiement" INT NOT NULL,
    "detailsTaxesBrut" NUMERIC(10, 2),
    FOREIGN KEY ("idCommande") REFERENCES "commande"("idCommande"),
    FOREIGN KEY ("idModePaiement") REFERENCES "modePaiement"("idModePaiement")
);


-- ==============================================================================
-- MODULE 5 : Dépenses & Imprévus
-- ==============================================================================

CREATE TABLE "depense" (
    "idDepense" SERIAL PRIMARY KEY,
    "idSession" INT,
    "idTypeDepense" INT NOT NULL,
    "montantDepense" NUMERIC(10, 2) NOT NULL,
    "raisonDetaillee" TEXT NOT NULL,
    "dateDepense" DATE NOT NULL,
    "idStatutValidationAdmin" INT NOT NULL,
    "commentaireAdminRetour" TEXT,
    FOREIGN KEY ("idSession") REFERENCES "sessionTruck"("idSession"),
    FOREIGN KEY ("idTypeDepense") REFERENCES "typeDepense"("idTypeDepense"),
    FOREIGN KEY ("idStatutValidationAdmin") REFERENCES "statutValidationAdmin"("idStatutValidationAdmin")
);


-- ==============================================================================
-- MODULE 6 : Satisfaction Client, Avis & Boost
-- ==============================================================================

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




-- view.sql
CREATE OR REPLACE VIEW "view_Itineraire_SessionTruck_Depense" AS
SELECT
    i."idItineraire",
    i."nomZone",

    s."idSession",
    s."idTruck",
    s."dateSession",
    s."fondDeCaisseOuverture",
    s."fondDeCaisseCloture",
    s."chiffreAffaireTotal",

    COALESCE(d."montantDepenseTotal",0) AS "montantDepenseTotal"

FROM "itineraire" i

INNER JOIN "sessionTruck" s
    ON i."idItineraire" = s."idItineraire"

LEFT JOIN (
    SELECT
        "idSession",
        SUM("montantDepense") AS "montantDepenseTotal"
    FROM "depense"
    GROUP BY "idSession"
) d
    ON s."idSession" = d."idSession";
    
-- =======================================================
-- INSERTION DES DONNÉES DE TEST 
-- =======================================================

-- data.sql
INSERT INTO "role" ("libelle") VALUES ('ADMIN'), ('VENDEUSE'), ('CUISINIER'), ('CHAUFFEUR'), ('REMPLACANT');

INSERT INTO "typeConge" ("libelle") VALUES ('CONGE_PAYE'), ('ABSENCE_MALADIE'), ('ABSENCE_INJUSTIFIEE'), ('CONGE_EXCEPTIONNEL');

INSERT INTO "statutValidation" ("libelle") VALUES ('EN_ATTENTE'), ('VALIDE'), ('REFUSE');

INSERT INTO "statutDisponibilite" ("libelle") VALUES ('DISPONIBLE'), ('EN_MAINTENANCE'), ('PANNE');

INSERT INTO "statutSession" ("libelle") VALUES ('OUVERTE'), ('CLOTUREE'), ('ANNULEE_METEO');

INSERT INTO "typeEquipement" ("libelle") VALUES ('EMBALLAGE'), ('CUILLERE'), ('REFRIGERATEUR'), ('MACHINE');

INSERT INTO "statutAlerte" ("libelle") VALUES ('OK'), ('SOLOINA'), ('TOKONY_VIDIANA');

INSERT INTO "methodeComptable" ("libelle") VALUES ('LIFO'), ('CUMP');

INSERT INTO "typeItem" ("libelle") VALUES ('INGREDIENT'), ('EQUIPEMENT');

INSERT INTO "typeCommande" ("libelle") VALUES ('SUR_PLACE'), ('A_EMPORTER'), ('EN_LIGNE');

INSERT INTO "statutCommande" ("libelle") VALUES ('EN_ATTENTE'), ('PREPARATION'), ('PRETE_POUR_RECUPERATION'), ('LIVREE'), ('ANNULEE');

INSERT INTO "typeTarification" ("libelle") VALUES ('HEURE_NORMALE'), ('HEURE_SUPP');

INSERT INTO "actionCommande" ("libelle") VALUES ('AJOUTER'), ('RETIRER');

INSERT INTO "modePaiement" ("libelle") VALUES ('ESPECE'), ('MOBILE_MONEY');

INSERT INTO "typeDepense" ("libelle") VALUES ('ACHAT_STOCKS'), ('CARBURANT'), ('REPARATION_TRUCK'), ('IMPREVUS_METEO'), ('REPAS_MIDI_CHEF');

INSERT INTO "statutValidationAdmin" ("libelle") VALUES ('EN_ATTENTE'), ('VALIDE_ADMIN'), ('REFUSE_ADMIN');

INSERT INTO "typeRetour" ("libelle") VALUES ('REMARQUE_AVIS'), ('DEMANDE_PRODUIT');

INSERT INTO "classificationSentiment" ("libelle") VALUES ('POSITIF'), ('NEGATIF'), ('NEUTRE');

INSERT INTO "statutDemandeAchat" ("libelle") VALUES ('NON_APPLICABLE'), ('DEMANDE_ACHAT_ENVOYEE_A_ADMIN'), ('APPROUVEE');

INSERT INTO "typeNotification" ("libelle") VALUES ('BOOST_NOUVEAU_PRODUIT'), ('ARRIVEE_POINT_DE_VENTE');

-- TYPE mouvement
INSERT INTO "typeMouvement" ("libelle") VALUES ('ENTREE'), ('SORTIE');

-- data1.sql
INSERT INTO "produit" ("nomProduit", "prixBase") VALUES
('Sandwich poulet', 5000.00),
('Burger boeuf', 7000.00),
('Hot dog', 4000.00),
('Salade cesar', 5500.00),
('Pizza fromage', 8000.00);

INSERT INTO "ingredient" ("nomIngredient", "seuilAlerteQuantite", "uniteMesure") VALUES
('Pain burger', 10.00, 'piece'),
('Blanc de poulet', 5.00, 'kg'),
('Steak boeuf', 5.00, 'kg'),
('Salade verte', 3.00, 'kg'),
('Tomate', 5.00, 'kg'),
('Fromage râpé', 5.00, 'kg'),
('Saucisse', 5.00, 'kg'),
('Pâte à pizza', 10.00, 'piece'),
('Sauce tomate', 5.00, 'L');

INSERT INTO "lotIngredient" ("idIngredient", "dateReception", "datePeremption", "quantiteInitiale", "prixAchatUnitaire")
VALUES 
(1, '2026-06-25', '2026-07-10', 50.00, 0.85),
(2, '2026-06-20', '2026-07-15', 15.00, 6.50),
(5, '2026-06-28', '2026-07-20', 25.00, 2.30);

INSERT INTO "recetteDeBase" ("idProduit", "idIngredient", "quantiteRecette") VALUES
(1, 2, 0.200),
(1, 4, 0.050),
(1, 5, 0.050),
(2, 1, 1.000),
(2, 3, 0.150),
(2, 6, 0.030),
(3, 1, 1.000),
(3, 7, 1.000),
(4, 4, 0.100),
(4, 5, 0.050),
(5, 8, 1.000),
(5, 9, 0.100),
(5, 6, 0.100);

INSERT INTO "itineraire" ("nomZone", "lieuExact", "heureDebutPrevue", "heureFinPrevue", "jourSemaine") VALUES
('Analakely', 'Devant la gare', '11:00:00', '14:00:00', 'LUNDI'),
('Ivandry', 'Pres du supermarché Leader Price', '17:00:00', '21:00:00', 'LUNDI'),
('Antanimena', 'Devant l université', '11:00:00', '14:00:00', 'MARDI'),
('Ankorondrano', 'Pres de Orange', '17:00:00', '21:00:00', 'MARDI'),
('Isotry', 'Marche d Isotry', '11:00:00', '15:00:00', 'MERCREDI'),
('Behoririka', 'Pres de la gare routière', '11:00:00', '14:00:00', 'JEUDI'),
('Anosy', 'Pres du lac Anosy', '17:00:00', '21:00:00', 'JEUDI'),
('Ambohijatovo', 'Devant la banque BNI', '11:00:00', '15:00:00', 'VENDREDI'),
('Antaninandro', 'Pres du marché', '17:00:00', '21:00:00', 'VENDREDI'),
('Ambodivona', 'Pres de Telma', '10:00:00', '14:00:00', 'SAMEDI');






-- ajout de truck et user
INSERT INTO "truck" ("immatriculation", "idStatutDisponibilite") VALUES
('1234 TMA', 1),
('5678 TMA', 1),
('9012 TMA', 1);

INSERT INTO "utilisateur" ("nom", "prenom", "email", "motDePasse", "idRole", "salaireBaseFixe", "statutActif") VALUES
('Rakoto', 'Jean', 'jean.rakoto@email.com', 'password123', 4, 500000.00, TRUE),
('Rabe', 'Paul', 'paul.rabe@email.com', 'password123', 4, 450000.00, TRUE),
('Randria', 'Marie', 'marie.randria@email.com', 'password123', 4, 480000.00, TRUE),
('Andria', 'Tiana', 'tiana.andria@email.com', 'password123', 4, 520000.00, TRUE),
('Ratsimba', 'Lalao', 'lalao.ratsimba@email.com', 'password123', 4, 470000.00, TRUE);




-- MODULE 3
-- 1. Équipements
INSERT INTO "equipement" ("nomEquipement", "idTypeEquipement", "idMethodeComptable", "prixUnitaire", "quantiteMin") VALUES
('Barquette sandwich', 1, 2, 50.00, 100.00),
('Sachet burger', 1, 2, 30.00, 100.00),
('Cuillère en bois', 2, 1, 100.00, 50.00),
('Fourchette', 2, 1, 80.00, 50.00),
('Réfrigérateur portable', 3, 2, 1500000.00, 1.00),
('Machine à café', 4, 2, 800000.00, 1.00),
('Plaque chauffante', 4, 2, 1200000.00, 1.00),
('Assiette carton', 1, 1, 25.00, 200.00),
('Verre en plastique', 1, 1, 15.00, 200.00),
('Congélateur', 3, 2, 2000000.00, 1.00);

-- 2. Mouvements des lots d'ingrédients
INSERT INTO "mouvementLotIngredient" ("idTypeMouvement", "idLot", "quantite", "dateMouvement") VALUES
(2, 1, -5.00, '2026-06-26'),
(2, 1, -3.00, '2026-06-27'),
(2, 1, -4.00, '2026-06-28'),
(2, 1, -3.00, '2026-06-29'),
(2, 2, -1.50, '2026-06-21'),
(2, 2, -2.00, '2026-06-22'),
(2, 2, -1.80, '2026-06-23'),
(2, 2, -1.20, '2026-06-24'),
(2, 3, -1.00, '2026-06-29'),
(2, 3, -2.00, '2026-06-30'),
(2, 3, -1.50, '2026-07-01'),
(2, 3, -0.50, '2026-07-02');

-- 3. Mouvements des équipements
INSERT INTO "mouvementEquipement" ("idTypeMouvement", "idEquipement", "quantite", "dateMouvement") VALUES
(1, 1, 200.00, '2026-06-20'),
(2, 1, -50.00, '2026-06-25'),
(2, 1, -30.00, '2026-06-27'),
(1, 2, 150.00, '2026-06-22'),
(2, 2, -40.00, '2026-06-26'),
(2, 2, -25.00, '2026-06-28'),
(1, 3, 50.00, '2026-06-24'),
(2, 3, -15.00, '2026-06-27'),
(1, 4, 50.00, '2026-06-24'),
(2, 4, -20.00, '2026-06-28'),
(1, 8, 300.00, '2026-06-25'),
(2, 8, -80.00, '2026-06-27'),
(1, 9, 250.00, '2026-06-26'),
(2, 9, -60.00, '2026-06-29');

-- INSERT INTO "sessionTruck" (
--     "idTruck", 
--     "idItineraire", 
--     "dateSession", 
--     "fondDeCaisseOuverture", 
--     "fondDeCaisseCloture", 
--     "chiffreAffaireTotal", 
--     "commissionTotaleEquipe", 
--     "idStatutSession"
-- ) VALUES 
-- (
--     1,  -- idTruck: 1234 TMA
--     1,  -- idItineraire: Analakely (devant la gare)
--     '2026-06-26', 
--     50000.00,  -- fond de caisse ouverture
--     75000.00,  -- fond de caisse clôture
--     125000.00, -- chiffre d'affaire total
--     25000.00,  -- commission totale équipe
--     1          -- idStatutSession: OUVERTE
-- ),
-- (
--     1,  -- idTruck: 1234 TMA
--     2,  -- idItineraire: Ivandry (Leader Price)
--     '2026-06-28', 
--     50000.00, 
--     82000.00, 
--     150000.00, 
--     30000.00, 
--     2          -- idStatutSession: CLOTUREE
-- ),
-- (
--     2,  -- idTruck: 5678 TMA
--     3,  -- idItineraire: Antanimena (université)
--     '2026-06-29', 
--     40000.00, 
--     70000.00, 
--     98000.00, 
--     19600.00, 
--     1          -- idStatutSession: OUVERTE
-- );

-- 4. Inventaires journaliers (à adapter selon vos idSession existantes)
-- INSERT INTO "inventaireJournalier" ("idSession", "dateInventaire", "idTypeItem", "idItem", "quantitePhysiqueConstatee", "quantiteTheoriqueSysteme", "ecartInventaire") VALUES
-- (1, '2026-06-26', 1, 1, 45.00, 45.00, 0.00),
-- (1, '2026-06-26', 1, 2, 13.50, 13.50, 0.00),
-- (1, '2026-06-26', 1, 5, 25.00, 24.50, 0.50),
-- (2, '2026-06-28', 1, 1, 32.00, 33.00, -1.00),
-- (2, '2026-06-28', 1, 2, 10.00, 10.50, -0.50),
-- (2, '2026-06-28', 1, 5, 22.00, 21.80, 0.20),
-- (1, '2026-06-27', 2, 1, 150.00, 150.00, 0.00),
-- (1, '2026-06-27', 2, 2, 110.00, 110.00, 0.00),
-- (1, '2026-06-27', 2, 8, 220.00, 220.00, 0.00),
-- (1, '2026-06-27', 2, 9, 190.00, 190.00, 0.00),
-- (3, '2026-06-29', 1, 1, 28.00, 29.00, -1.00),
-- (3, '2026-06-29', 1, 5, 20.00, 20.50, -0.50),
-- (3, '2026-06-29', 2, 1, 120.00, 120.00, 0.00),
-- (3, '2026-06-29', 2, 8, 140.00, 140.00, 0.00);


-- SELECT VERIFICATION pour module3
SELECT * FROM "ingredient" ;

SELECT * FROM "lotIngredient" ;

SELECT * FROM "equipement" ;

SELECT * FROM "typeMouvement" ;

SELECT * FROM "mouvementLotIngredient" ;

SELECT * FROM "mouvementEquipement" ;

SELECT * FROM "inventaireJournalier" ;