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
-- DONNÉES DE TEST POUR LA LOCALISATION
-- ============================================

-- 1. Insertion des utilisateurs
INSERT INTO "utilisateur" ("nom", "prenom", "email", "motDePasse", "idRole", "salaireBaseFixe", "statutActif") VALUES 
('Rakoto', 'Jean', 'jean.rakoto@foodtruck.com', 'password123', 1, 2500.00, true),
('Rabe', 'Marie', 'marie.rabe@foodtruck.com', 'password123', 1, 2200.00, true),
('Randria', 'Pierre', 'pierre.randria@foodtruck.com', 'password123', 4, 1800.00, true),
('Rasoa', 'Sophie', 'sophie.rasoa@foodtruck.com', 'password123', 2, 1500.00, true),
('Rajaonarivony', 'Lala', 'lala.rajaonarivony@foodtruck.com', 'password123', 3, 1600.00, true),
('Andriamihaja', 'Tiana', 'tiana.andriamihaja@foodtruck.com', 'password123', 4, 1800.00, true),
('Rakotomalala', 'Hery', 'hery.rakotomalala@foodtruck.com', 'password123', 2, 1500.00, true),
('Razanakoto', 'Faneva', 'faneva.razanakoto@foodtruck.com', 'password123', 3, 1600.00, true);

-- 2. Insertion des trucks
INSERT INTO "truck" ("immatriculation", "idStatutDisponibilite") VALUES 
('1234 TMA', 1),  -- DISPONIBLE
('5678 TMA', 1),  -- DISPONIBLE
('9012 TMA', 1),  -- DISPONIBLE
('3456 TMA', 2),  -- EN_MAINTENANCE
('7890 TMA', 1),  -- DISPONIBLE
('2345 TMA', 1),  -- DISPONIBLE
('6789 TMA', 3),  -- PANNE
('0123 TMA', 1);  -- DISPONIBLE

-- 3. Insertion des itinéraires
INSERT INTO "itineraire" ("nomZone", "lieuExact", "heureDebutPrevue", "heureFinPrevue", "jourSemaine") VALUES 
('Analakely', 'Devant la gare routière', '11:00:00', '14:00:00', 'LUNDI'),
('Ivandry', 'Leader Price - Parking client', '17:00:00', '21:00:00', 'MARDI'),
('Antanimena', 'Université - Entrée principale', '11:00:00', '14:00:00', 'MERCREDI'),
('Ankorondrano', 'Devant Orange Digital Center', '16:00:00', '20:00:00', 'JEUDI'),
('Isotry', 'Marché couvert - Côté est', '10:00:00', '13:00:00', 'VENDREDI'),
('Behoririka', 'Gare routière - Arrêt bus n°5', '12:00:00', '15:00:00', 'SAMEDI'),
('Anosy', 'Lac Anosy - Côté sud', '14:00:00', '18:00:00', 'DIMANCHE'),
('Ambohijatovo', 'Devant BNI Madagascar', '11:00:00', '14:00:00', 'LUNDI'),
('Antaninandro', 'Marché populaire - Entrée nord', '16:00:00', '20:00:00', 'MARDI'),
('Ambodivona', 'Devant Telma - Avenue de l''Indépendance', '12:00:00', '15:00:00', 'MERCREDI'),
('Mahamasina', 'Stade - Parking sud', '09:00:00', '12:00:00', 'JEUDI'),
('Andravoahangy', 'Marché - Côté ouest', '14:00:00', '17:00:00', 'VENDREDI'),
('Ampasanimalo', 'Devant le Palais des Sports', '10:00:00', '13:00:00', 'SAMEDI'),
('Ankadifotsy', 'Carrefour - Côté est', '15:00:00', '19:00:00', 'DIMANCHE'),
('67ha', 'Proximité du Lycée Français', '11:00:00', '14:00:00', 'LUNDI');

-- 4. Insertion des sessions de truck
INSERT INTO "sessionTruck" ("idTruck", "idItineraire", "dateSession", "fondDeCaisseOuverture", "fondDeCaisseCloture", "chiffreAffaireTotal", "commissionTotaleEquipe", "idStatutSession") VALUES 
-- Sessions OUVERTES (pour publication)
(1, 1, CURRENT_DATE, 500.00, NULL, 0.00, 0.00, 1),  -- Session 1: 1234 TMA
(2, 2, CURRENT_DATE, 300.00, NULL, 0.00, 0.00, 1),  -- Session 2: 5678 TMA
(3, 3, CURRENT_DATE, 400.00, NULL, 0.00, 0.00, 1),  -- Session 3: 9012 TMA
(5, 6, CURRENT_DATE, 450.00, NULL, 0.00, 0.00, 1),  -- Session 4: 7890 TMA
(6, 7, CURRENT_DATE, 350.00, NULL, 0.00, 0.00, 1),  -- Session 5: 2345 TMA
(8, 8, CURRENT_DATE, 500.00, NULL, 0.00, 0.00, 1),  -- Session 6: 0123 TMA

-- Sessions CLOTUREES (historique)
(1, 4, CURRENT_DATE - 1, 500.00, 580.00, 350.00, 35.00, 2),  -- Session 7
(2, 5, CURRENT_DATE - 2, 300.00, 420.00, 280.00, 28.00, 2),  -- Session 8
(3, 9, CURRENT_DATE - 3, 400.00, 510.00, 320.00, 32.00, 2),  -- Session 9
(5, 10, CURRENT_DATE - 1, 450.00, 600.00, 380.00, 38.00, 2), -- Session 10

-- Sessions à venir (pour publication future)
(1, 11, CURRENT_DATE + 1, 500.00, NULL, 0.00, 0.00, 1),  -- Session 11
(2, 12, CURRENT_DATE + 1, 300.00, NULL, 0.00, 0.00, 1),  -- Session 12
(3, 13, CURRENT_DATE + 2, 400.00, NULL, 0.00, 0.00, 1);  -- Session 13

-- 5. Insertion des équipes de session
INSERT INTO "equipeSession" ("idSession", "idUtilisateur", "idRoleDuJour", "salaireJournalierRemplacant") VALUES 
-- Session 1: 1234 TMA - Analakely
(1, 3, 4, NULL),  -- CHAUFFEUR
(1, 4, 2, NULL),  -- VENDEUSE
(1, 5, 3, NULL),  -- CUISINIER

-- Session 2: 5678 TMA - Ivandry
(2, 6, 4, NULL),  -- CHAUFFEUR
(2, 7, 2, NULL),  -- VENDEUSE
(2, 8, 3, NULL),  -- CUISINIER

-- Session 3: 9012 TMA - Antanimena
(3, 3, 4, NULL),  -- CHAUFFEUR
(3, 4, 2, NULL),  -- VENDEUSE
(3, 5, 3, NULL),  -- CUISINIER

-- Session 4: 7890 TMA - Behoririka
(4, 6, 4, NULL),  -- CHAUFFEUR
(4, 7, 2, NULL),  -- VENDEUSE

-- Session 5: 2345 TMA - Anosy
(5, 3, 4, NULL),  -- CHAUFFEUR
(5, 4, 2, NULL),  -- VENDEUSE
(5, 5, 3, NULL),  -- CUISINIER

-- Session 6: 0123 TMA - Ambohijatovo
(6, 6, 4, NULL),  -- CHAUFFEUR
(6, 7, 2, NULL),  -- VENDEUSE
(6, 8, 3, NULL),  -- CUISINIER

-- Session 7: 1234 TMA - Ankorondrano (clôturée)
(7, 3, 4, NULL),  -- CHAUFFEUR
(7, 4, 2, NULL),  -- VENDEUSE

-- Session 8: 5678 TMA - Isotry (clôturée)
(8, 6, 4, NULL),  -- CHAUFFEUR
(8, 7, 2, NULL),  -- VENDEUSE

-- Session 11: 1234 TMA - Mahamasina (à venir)
(11, 3, 4, NULL),  -- CHAUFFEUR
(11, 4, 2, NULL),  -- VENDEUSE
(11, 5, 3, NULL);  -- CUISINIER

-- 6. Insertion des positions des trucks (SESSION_TRUCK_POSITION)
INSERT INTO "sessionTruckPosition" ("idSession", "idItineraire", "heureArrivee", "datePublication") VALUES 
-- Positions du jour (sessions ouvertes)
(1, 1, '11:00:00', CURRENT_DATE),      -- 1234 TMA à Analakely
(2, 2, '17:30:00', CURRENT_DATE),      -- 5678 TMA à Ivandry
(3, 3, '11:15:00', CURRENT_DATE),      -- 9012 TMA à Antanimena
(4, 6, '12:00:00', CURRENT_DATE),      -- 7890 TMA à Behoririka
(5, 7, '14:30:00', CURRENT_DATE),      -- 2345 TMA à Anosy
(6, 8, '11:00:00', CURRENT_DATE),      -- 0123 TMA à Ambohijatovo

-- Positions d'hier (sessions clôturées)
(7, 4, '16:00:00', CURRENT_DATE - 1),  -- 1234 TMA à Ankorondrano
(8, 5, '10:30:00', CURRENT_DATE - 2),  -- 5678 TMA à Isotry
(9, 9, '16:00:00', CURRENT_DATE - 3),  -- 9012 TMA à Antaninandro
(10, 10, '12:00:00', CURRENT_DATE - 1), -- 7890 TMA à Ambodivona

-- Positions à venir
(11, 11, '09:00:00', CURRENT_DATE),    -- 1234 TMA à Mahamasina (publié aujourd'hui)
(12, 12, '14:00:00', CURRENT_DATE),    -- 5678 TMA à Andravoahangy (publié aujourd'hui)
(13, 13, '10:00:00', CURRENT_DATE + 1); -- 9012 TMA à Ampasanimalo (publié demain)

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
(2, 7, 1, CURRENT_TIMESTAMP - INTERVAL '1 hour', 22.50, 4, 1),
(3, 4, 3, CURRENT_TIMESTAMP - INTERVAL '3 hours', 19.80, 4, 1),
(4, 7, 2, CURRENT_TIMESTAMP - INTERVAL '30 minutes', 12.50, 3, 1);

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