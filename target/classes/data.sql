-- Réinitialisation des données de test
DELETE FROM "utilisateur" WHERE "email" = 'admin@gmail.com';

INSERT INTO "role" ("libelle")
SELECT libelle
FROM (VALUES
    ('ADMIN'),
    ('EMPLOYE'),
    ('VENDEUSE'),
    ('CUISINIER'),
    ('CHAUFFEUR'),
    ('REMPLACANT')
) AS roles(libelle)
WHERE NOT EXISTS (
    SELECT 1 FROM "role" r WHERE r."libelle" = roles.libelle
);

INSERT INTO "typeConge" ("libelle") VALUES ('CONGE_PAYE'), ('ABSENCE_MALADIE'), ('ABSENCE_INJUSTIFIEE'), ('CONGE_EXCEPTIONNEL');

INSERT INTO "statutValidation" ("libelle") VALUES ('EN_ATTENTE'), ('VALIDE'), ('REFUSE');

INSERT INTO "statutDisponibilite" ("libelle") VALUES ('DISPONIBLE'), ('EN_MAINTENANCE'), ('PANNE');

INSERT INTO "statutSession" ("libelle") VALUES ('OUVERTE'), ('CLOTUREE'), ('ANNULEE_METEO');

INSERT INTO "typeEquipement" ("libelle") VALUES ('EMBALLAGE'), ('CUILLERE'), ('REFRIGERATEUR'), ('MACHINE');

INSERT INTO "statutAlerte" ("libelle") VALUES ('OK'), ('SOLOINA'), ('TOKONY_VIDIANA');

INSERT INTO "methodeComptable" ("libelle") VALUES ('LIFO'), ('CUMP');

INSERT INTO "typeItem" ("libelle") VALUES ('INGREDIENT'), ('EQUIPEMENT');

INSERT INTO "typeCommande" ("libelle") VALUES ('SUR_PLACE'), ('A_EMPORTER'), ('EN_LIGNE'), ('A_DISTANCE');

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

INSERT INTO "utilisateur" ("nom", "prenom", "email", "motDePasse", "idRole", "statutActif")
VALUES (
    'Admin',
    'Super',
    'admin@gmail.com',
    '$2b$10$OXY0OWLG800lJP4vQCjv6.snIN19CZlLQNshWrfHWZdN5zoSuOqfy',
    (SELECT "idRole" FROM "role" WHERE "libelle" = 'ADMIN' ORDER BY "idRole" LIMIT 1),
    TRUE
);
