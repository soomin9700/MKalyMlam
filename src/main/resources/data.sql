-- ============================================
-- DONNÉES DE BASE (TABLES DE RÉFÉRENCE)
-- ============================================

INSERT INTO "role" ("libelle") VALUES 
('ADMIN'), 
('VENDEUSE'), 
('CUISINIER'), 
('CHAUFFEUR'), 
('REMPLACANT');

INSERT INTO "typeConge" ("libelle") VALUES 
('CONGE_PAYE'), 
('ABSENCE_MALADIE'), 
('ABSENCE_INJUSTIFIEE'), 
('CONGE_EXCEPTIONNEL');

INSERT INTO "statutValidation" ("libelle") VALUES 
('EN_ATTENTE'), 
('VALIDE'), 
('REFUSE');

INSERT INTO "statutDisponibilite" ("libelle") VALUES 
('DISPONIBLE'), 
('EN_MAINTENANCE'), 
('PANNE');

INSERT INTO "statutSession" ("libelle") VALUES 
('OUVERTE'), 
('CLOTUREE'), 
('ANNULEE_METEO');

INSERT INTO "typeEquipement" ("libelle") VALUES 
('EMBALLAGE'), 
('CUILLERE'), 
('REFRIGERATEUR'), 
('MACHINE');

INSERT INTO "statutAlerte" ("libelle") VALUES 
('OK'), 
('SOLOINA'), 
('TOKONY_VIDIANA');

INSERT INTO "methodeComptable" ("libelle") VALUES 
('LIFO'), 
('CUMP');

INSERT INTO "typeItem" ("libelle") VALUES 
('INGREDIENT'), 
('EQUIPEMENT');

INSERT INTO "typeCommande" ("libelle") VALUES 
('SUR_PLACE'), 
('A_EMPORTER'), 
('EN_LIGNE'), 
('A_DISTANCE');

INSERT INTO "statutCommande" ("libelle") VALUES 
('EN_ATTENTE'), 
('PREPARATION'), 
('PRETE_POUR_RECUPERATION'), 
('LIVREE'), 
('ANNULEE');

INSERT INTO "typeTarification" ("libelle") VALUES 
('HEURE_NORMALE'), 
('HEURE_SUPP');

INSERT INTO "actionCommande" ("libelle") VALUES 
('AJOUTER'), 
('RETIRER');

INSERT INTO "modePaiement" ("libelle") VALUES 
('ESPECE'), 
('MOBILE_MONEY');

INSERT INTO "typeDepense" ("libelle") VALUES 
('ACHAT_STOCKS'), 
('CARBURANT'), 
('REPARATION_TRUCK'), 
('IMPREVUS_METEO'), 
('REPAS_MIDI_CHEF');

INSERT INTO "statutValidationAdmin" ("libelle") VALUES 
('EN_ATTENTE'), 
('VALIDE_ADMIN'), 
('REFUSE_ADMIN');

INSERT INTO "typeRetour" ("libelle") VALUES 
('REMARQUE_AVIS'), 
('DEMANDE_PRODUIT');

INSERT INTO "classificationSentiment" ("libelle") VALUES 
('POSITIF'), 
('NEGATIF'), 
('NEUTRE');

INSERT INTO "typeMouvement" ("libelle") VALUES ('ENTREE'), ('SORTIE');

INSERT INTO "typeNotification" ("libelle") VALUES ('BOOST_NOUVEAU_PRODUIT'), ('ARRIVEE_POINT_DE_VENTE'), ('ALERTE_STOCK'), ('HEURE_RECUPERATION'), ('COMMANDE_ANNULEE');
INSERT INTO "statutDemandeAchat" ("libelle") VALUES 
('NON_APPLICABLE'), 
('DEMANDE_ACHAT_ENVOYEE_A_ADMIN'), 
('APPROUVEE');

INSERT INTO "typeNotification" ("libelle") VALUES 
('BOOST_NOUVEAU_PRODUIT'), 
('ARRIVEE_POINT_DE_VENTE');

INSERT INTO "typeMouvement" ("libelle") VALUES 
('ENTREE'), 
('SORTIE');

-- ============================================
-- DONNÉES DE TEST POUR LA LOCALISATION (2 TRUCKS)
-- ============================================

-- 1. Insertion des utilisateurs (réduit)
INSERT INTO "utilisateur" ("nom", "prenom", "email", "motDePasse", "idRole", "salaireBaseFixe", "statutActif") VALUES 
('Rakoto', 'Jean', 'jean.rakoto@foodtruck.com', 'password123', 1, 2500.00, true),
('Rabe', 'Marie', 'marie.rabe@foodtruck.com', 'password123', 1, 2200.00, true),
('Randria', 'Pierre', 'pierre.randria@foodtruck.com', 'password123', 4, 1800.00, true),
('Rasoa', 'Sophie', 'sophie.rasoa@foodtruck.com', 'password123', 2, 1500.00, true),
('Rajaonarivony', 'Lala', 'lala.rajaonarivony@foodtruck.com', 'password123', 3, 1600.00, true);

-- 2. Insertion des trucks (seulement 2)
INSERT INTO "truck" ("immatriculation", "idStatutDisponibilite") VALUES 
('1234 TMA', 1),  -- DISPONIBLE
('5678 TMA', 1);  -- DISPONIBLE

-- 3. Insertion des itinéraires (réduit)
INSERT INTO "itineraire" ("nomZone", "lieuExact", "heureDebutPrevue", "heureFinPrevue", "jourSemaine") VALUES 
('Analakely', 'Devant la gare routière', '11:00:00', '14:00:00', 'LUNDI'),
('Ivandry', 'Leader Price - Parking client', '17:00:00', '21:00:00', 'MARDI'),
('Antanimena', 'Université - Entrée principale', '11:00:00', '14:00:00', 'MERCREDI'),
('Ankorondrano', 'Devant Orange Digital Center', '16:00:00', '20:00:00', 'JEUDI'),
('Isotry', 'Marché couvert - Côté est', '10:00:00', '13:00:00', 'VENDREDI'),
('Behoririka', 'Gare routière - Arrêt bus n°5', '12:00:00', '15:00:00', 'SAMEDI'),
('Anosy', 'Lac Anosy - Côté sud', '14:00:00', '18:00:00', 'DIMANCHE'),
('Ambohijatovo', 'Devant BNI Madagascar', '11:00:00', '14:00:00', 'LUNDI');

-- 4. Insertion des sessions de truck (pour 2 trucks)
INSERT INTO "sessionTruck" ("idTruck", "idItineraire", "dateSession", "fondDeCaisseOuverture", "fondDeCaisseCloture", "chiffreAffaireTotal", "commissionTotaleEquipe", "idStatutSession") VALUES 
-- Sessions OUVERTES (pour publication)
(1, 1, CURRENT_DATE, 500.00, NULL, 0.00, 0.00, 1),  -- Session 1: 1234 TMA
(2, 2, CURRENT_DATE, 300.00, NULL, 0.00, 0.00, 1),  -- Session 2: 5678 TMA

-- Sessions CLOTUREES (historique)
(1, 3, CURRENT_DATE - 1, 500.00, 580.00, 350.00, 35.00, 2),  -- Session 3: 1234 TMA
(2, 4, CURRENT_DATE - 2, 300.00, 420.00, 280.00, 28.00, 2),  -- Session 4: 5678 TMA

-- Sessions à venir (pour publication future)
(1, 5, CURRENT_DATE + 1, 500.00, NULL, 0.00, 0.00, 1),  -- Session 5: 1234 TMA
(2, 6, CURRENT_DATE + 1, 300.00, NULL, 0.00, 0.00, 1);  -- Session 6: 5678 TMA

-- 5. Insertion des équipes de session
INSERT INTO "equipeSession" ("idSession", "idUtilisateur", "idRoleDuJour", "salaireJournalierRemplacant") VALUES 
-- Session 1: 1234 TMA - Analakely
(1, 3, 4, NULL),  -- CHAUFFEUR
(1, 4, 2, NULL),  -- VENDEUSE
(1, 5, 3, NULL),  -- CUISINIER

-- Session 2: 5678 TMA - Ivandry
(2, 3, 4, NULL),  -- CHAUFFEUR
(2, 4, 2, NULL),  -- VENDEUSE
(2, 5, 3, NULL),  -- CUISINIER

-- Session 3: 1234 TMA - Antanimena (clôturée)
(3, 3, 4, NULL),  -- CHAUFFEUR
(3, 4, 2, NULL),  -- VENDEUSE

-- Session 4: 5678 TMA - Ankorondrano (clôturée)
(4, 3, 4, NULL),  -- CHAUFFEUR
(4, 4, 2, NULL),  -- VENDEUSE

-- Session 5: 1234 TMA - Isotry (à venir)
(5, 3, 4, NULL),  -- CHAUFFEUR
(5, 4, 2, NULL),  -- VENDEUSE
(5, 5, 3, NULL);  -- CUISINIER

-- 6. Insertion des positions des trucks (SESSION_TRUCK_POSITION)
INSERT INTO "sessionTruckPosition" ("idSession", "idItineraire", "heureArrivee", "datePublication") VALUES 
-- Positions du jour (sessions ouvertes)
(1, 1, '11:00:00', CURRENT_DATE),      -- 1234 TMA à Analakely
(2, 2, '17:30:00', CURRENT_DATE),      -- 5678 TMA à Ivandry

-- Positions d'hier (sessions clôturées)
(3, 3, '11:15:00', CURRENT_DATE - 1),  -- 1234 TMA à Antanimena
(4, 4, '16:00:00', CURRENT_DATE - 2),  -- 5678 TMA à Ankorondrano

-- Positions à venir
(5, 5, '10:00:00', CURRENT_DATE),      -- 1234 TMA à Isotry (publié aujourd'hui)
(6, 6, '12:00:00', CURRENT_DATE + 1);  -- 5678 TMA à Behoririka (publié demain)

-- 7. Insertion de produits (pour le menu)
INSERT INTO "produit" ("nomProduit", "prixBase", "estNouveau", "dateCreation") VALUES 
('Burger Signature', 8.90, false, CURRENT_DATE - 30),
('Burger Double Cheese', 11.90, false, CURRENT_DATE - 30),
('Burger Bacon', 10.90, false, CURRENT_DATE - 25),
('Tacos XXL', 7.50, false, CURRENT_DATE - 20),
('Tacos Poulet', 7.00, false, CURRENT_DATE - 20),
('Tacos Viande Grillée', 7.50, false, CURRENT_DATE - 20),
('Hot Dog New-Yorkais', 6.50, false, CURRENT_DATE - 15),
('Hot Dog Cheese', 7.00, false, CURRENT_DATE - 15),
('Hot Dog Bacon', 7.50, false, CURRENT_DATE - 15),
('Tteokbokki Coréen', 9.90, true, CURRENT_DATE - 5),
('Samoussa Poulet (5 pièces)', 5.90, true, CURRENT_DATE - 5),
('Samoussa Viande (5 pièces)', 6.50, true, CURRENT_DATE - 5),
('Tofu Frit Épicé', 6.50, true, CURRENT_DATE - 5),
('Nems (5 pièces)', 5.50, true, CURRENT_DATE - 3),
('Frites Maison', 3.50, false, CURRENT_DATE - 20),
('Boisson gazeuse', 2.50, false, CURRENT_DATE - 30),
('Eau minérale', 1.50, false, CURRENT_DATE - 30),
('Jus de fruit frais', 3.00, false, CURRENT_DATE - 15);

-- 8. Insertion d'ingrédients
INSERT INTO "ingredient" ("nomIngredient", "seuilAlerteQuantite", "uniteMesure", "actif") VALUES 
('Pain à burger', 20, 'pièce', true),
('Steak haché 150g', 10, 'kg', true),
('Steak poulet', 8, 'kg', true),
('Cheddar', 5, 'kg', true),
('Emmental', 4, 'kg', true),
('Salade verte', 10, 'kg', true),
('Tomate', 8, 'kg', true),
('Oignon rouge', 10, 'kg', true),
('Cornichons', 5, 'kg', true),
('Sauce maison', 5, 'L', true),
('Sauce barbecue', 4, 'L', true),
('Sauce épicée', 3, 'L', true),
('Galette de riz', 15, 'pièce', true),
('Poulet', 8, 'kg', true),
('Tofu', 6, 'kg', true),
('Pâte à nems', 10, 'pièce', true),
('Pâte à samoussa', 10, 'pièce', true),
('Pommes de terre', 15, 'kg', true),
('Huile de friture', 10, 'L', true),
('Farine', 20, 'kg', true);

-- 9. Insertion des recettes de base (association produit-ingrédient)
INSERT INTO "recetteDeBase" ("idProduit", "idIngredient", "quantiteRecette") VALUES 
-- Burger Signature
(1, 1, 1),   -- 1 pain
(1, 2, 0.15), -- 150g de steak
(1, 4, 0.05), -- 50g de cheddar
(1, 6, 0.02), -- 20g de salade
(1, 7, 0.03), -- 30g de tomate
(1, 10, 0.02), -- 20ml de sauce

-- Tacos XXL
(4, 3, 0.20), -- 200g de poulet
(4, 4, 0.05), -- 50g de cheddar
(4, 6, 0.02), -- 20g de salade
(4, 12, 0.03), -- 30ml de sauce épicée

-- Hot Dog New-Yorkais
(7, 1, 1),   -- 1 pain
(7, 2, 0.12), -- 120g de steak
(7, 9, 0.02), -- 20g de cornichons
(7, 11, 0.02); -- 20ml de sauce barbecue

-- 10. Insertion des lots d'ingrédients
INSERT INTO "lotIngredient" ("idIngredient", "dateReception", "datePeremption", "quantiteInitiale", "prixAchatUnitaire") VALUES 
(1, CURRENT_DATE - 5, CURRENT_DATE + 10, 50, 0.80),
(2, CURRENT_DATE - 3, CURRENT_DATE + 15, 20, 8.50),
(4, CURRENT_DATE - 4, CURRENT_DATE + 20, 15, 6.00),
(6, CURRENT_DATE - 2, CURRENT_DATE + 7, 10, 2.50),
(10, CURRENT_DATE - 5, CURRENT_DATE + 30, 8, 3.00),
(13, CURRENT_DATE - 6, CURRENT_DATE + 45, 30, 0.50),
(16, CURRENT_DATE - 3, CURRENT_DATE + 60, 50, 0.30);

-- 11. Insertion des mouvements de lots (entrées)
INSERT INTO "mouvementLotIngredient" ("idLot", "idTypeMouvement", "quantite", "dateMouvement") VALUES 
(1, 1, 50, CURRENT_DATE - 5),
(2, 1, 20, CURRENT_DATE - 3),
(3, 1, 15, CURRENT_DATE - 4),
(4, 1, 10, CURRENT_DATE - 2),
(5, 1, 8, CURRENT_DATE - 5),
(6, 1, 30, CURRENT_DATE - 6),
(7, 1, 50, CURRENT_DATE - 3);

-- 12. Insertion des équipements
INSERT INTO "equipement" ("nomEquipement", "idTypeEquipement", "idMethodeComptable", "prixUnitaire", "quantiteMin") VALUES 
('Barquette burger', 1, 2, 0.50, 20),
('Cuillère en bois', 2, 2, 1.00, 10),
('Réfrigérateur', 3, 1, 500.00, 1),
('Machine à frites', 4, 1, 300.00, 1);

-- 13. Insertion des avis clients
INSERT INTO "retourClient" ("idTypeRetour", "noteSur10", "contenuTexte", "idClassificationSentiment", "estPopulaire", "dateSoumission") VALUES 
(1, 9, 'Le burger Signature est incroyable ! Viande juteuse et sauce maison délicieuse. Je recommande vivement !', 1, true, CURRENT_DATE - 3),
(1, 8, 'Les tacos sont très bons et bien garnis. Rapport qualité-prix excellent.', 1, true, CURRENT_DATE - 4),
(1, 10, 'Le Tteokbokki est une vraie découverte, épicé juste comme il faut. Un voyage en Corée !', 1, true, CURRENT_DATE - 2),
(1, 7, 'Bon rapport qualité-prix, service rapide. Je reviendrai pour goûter les samoussas.', 1, false, CURRENT_DATE - 1),
(1, 9, 'Service rapide et souriant. Le hot dog New-Yorkais est excellent !', 1, true, CURRENT_DATE - 5),
(2, 8, 'J''aimerais que vous proposiez des options végétariennes supplémentaires.', 2, false, CURRENT_DATE - 3),
(1, 9, 'Les nems sont croustillants et bien garnis. Parfaits en entrée !', 1, true, CURRENT_DATE - 2),
(1, 10, 'Meilleur street food de la ville ! Les burgers sont faits maison avec des produits frais.', 1, true, CURRENT_DATE - 1);

-- 14. Insertion de quelques commandes
INSERT INTO "commande" ("idSession", "idVendeuse", "idTypeCommande", "dateHeureCreation", "montantTotal", "idStatutCommande", "idTypeTarification") VALUES 
(1, 4, 1, CURRENT_TIMESTAMP - INTERVAL '2 hours', 15.40, 4, 1),
(1, 4, 2, CURRENT_TIMESTAMP - INTERVAL '1.5 hours', 8.90, 4, 1),
(2, 4, 1, CURRENT_TIMESTAMP - INTERVAL '1 hour', 22.50, 4, 1),
(3, 4, 3, CURRENT_TIMESTAMP - INTERVAL '3 hours', 19.80, 4, 1),
(4, 4, 2, CURRENT_TIMESTAMP - INTERVAL '30 minutes', 12.50, 3, 1);

-- 15. Insertion des lignes de commande
INSERT INTO "ligneCommande" ("idCommande", "idProduit", "quantite", "prixUnitaireFacture") VALUES 
(1, 1, 1, 8.90),
(1, 15, 1, 3.50),
(1, 16, 1, 3.00),
(2, 1, 1, 8.90),
(3, 3, 1, 10.90),
(3, 15, 1, 3.50),
(3, 17, 2, 1.50),
(4, 2, 1, 11.90),
(4, 7, 1, 6.50),
(5, 4, 1, 7.50),
(5, 16, 1, 3.00);

-- 16. Insertion des factures
INSERT INTO "factureRecu" ("idCommande", "referenceFacture", "dateFacturation", "idModePaiement", "detailsTaxesBrut") VALUES 
(1, 'FACT-2026-001', CURRENT_TIMESTAMP - INTERVAL '2 hours', 1, 1.50),
(2, 'FACT-2026-002', CURRENT_TIMESTAMP - INTERVAL '1.5 hours', 2, 0.89),
(3, 'FACT-2026-003', CURRENT_TIMESTAMP - INTERVAL '1 hour', 1, 2.25),
(4, 'FACT-2026-004', CURRENT_TIMESTAMP - INTERVAL '3 hours', 2, 1.98),
(5, 'FACT-2026-005', CURRENT_TIMESTAMP - INTERVAL '30 minutes', 1, 1.25);

-- ============================================
-- REQUÊTES DE VÉRIFICATION
-- ============================================

-- Vérifier les sessions ouvertes
SELECT * FROM "sessionTruck" WHERE "idStatutSession" = 1;

-- Vérifier les positions publiées
SELECT 
    t."immatriculation",
    i."nomZone",
    i."lieuExact",
    sp."heureArrivee",
    sp."datePublication",
    ss."libelle" as statut_session
FROM "sessionTruckPosition" sp
JOIN "sessionTruck" st ON sp."idSession" = st."idSession"
JOIN "truck" t ON st."idTruck" = t."idTruck"
JOIN "itineraire" i ON sp."idItineraire" = i."idItineraire"
JOIN "statutSession" ss ON st."idStatutSession" = ss."idStatutSession"
ORDER BY sp."datePublication" DESC, sp."heureArrivee";

-- Vérifier les trucks disponibles
SELECT 
    t."idTruck",
    t."immatriculation",
    sd."libelle" as statut,
    COUNT(st."idSession") as nb_sessions
FROM "truck" t
LEFT JOIN "statutDisponibilite" sd ON t."idStatutDisponibilite" = sd."idStatutDisponibilite"
LEFT JOIN "sessionTruck" st ON t."idTruck" = st."idTruck" AND st."idStatutSession" = 1
GROUP BY t."idTruck", t."immatriculation", sd."libelle"
ORDER BY t."idTruck";
