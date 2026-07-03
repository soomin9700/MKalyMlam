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
