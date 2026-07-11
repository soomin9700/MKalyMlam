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
    "quantiteInitiale" NUMERIC(10, 2) NOT NULL,
    "prixAchatUnitaire" NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient")
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



-- DONNÉES MODULE 3
-- ============================================
-- MODULE 3 : STOCKS, LOTS & INVENTAIRES
-- DONNÉES DE TEST MISES À JOUR
-- ============================================

-- ============================================
-- 1. Nettoyer les données existantes (optionnel)
-- ============================================
-- ATTENTION : Ces commandes suppriment les données
/*
DELETE FROM "mouvementLotIngredient";
DELETE FROM "mouvementEquipement";
DELETE FROM "lotIngredient";
DELETE FROM "ingredient";
DELETE FROM "equipement";
*/

-- ============================================
-- 2. Ingrédients (table ingredient)
-- ============================================
INSERT INTO "ingredient" ("idIngredient", "nomIngredient", "seuilAlerteQuantite", "uniteMesure", "actif") VALUES
(1, 'Pain burger', 10.00, 'piece', TRUE),
(2, 'Blanc de poulet', 5.00, 'kg', TRUE),
(3, 'Steak boeuf', 5.00, 'kg', TRUE),
(4, 'Salade verte', 3.00, 'kg', TRUE),
(5, 'Tomate', 5.00, 'kg', TRUE),
(6, 'Fromage râpé', 5.00, 'kg', TRUE),
(7, 'Saucisse', 5.00, 'kg', TRUE),
(8, 'Pâte à pizza', 10.00, 'piece', TRUE),
(9, 'Sauce tomate', 5.00, 'L', TRUE),
(10, 'Oignon', 5.00, 'kg', TRUE),
(11, 'Mayonnaise', 3.00, 'L', TRUE),
(12, 'Ketchup', 3.00, 'L', TRUE),
(13, 'Moutarde', 2.00, 'L', TRUE),
(14, 'Bacon', 4.00, 'kg', TRUE),
(15, 'Champignon', 4.00, 'kg', TRUE);

-- ============================================
-- 3. Lots d'ingrédients (table lotIngredient)
-- ============================================
INSERT INTO "lotIngredient" ("idLot", "idIngredient", "dateReception", "datePeremption", "quantiteInitiale", "prixAchatUnitaire") VALUES
-- Pain burger (idIngredient = 1)
(1, 1, '2026-06-25', '2026-07-10', 50.00, 850.00),
(2, 1, '2026-07-01', '2026-07-15', 30.00, 900.00),
(3, 1, '2026-07-05', '2026-07-20', 40.00, 880.00),

-- Blanc de poulet (idIngredient = 2)
(4, 2, '2026-06-20', '2026-07-15', 15.00, 6500.00),
(5, 2, '2026-07-02', '2026-07-25', 12.00, 6800.00),

-- Steak boeuf (idIngredient = 3)
(6, 3, '2026-06-22', '2026-07-20', 20.00, 7500.00),
(7, 3, '2026-07-03', '2026-07-28', 15.00, 7800.00),

-- Salade verte (idIngredient = 4)
(8, 4, '2026-06-28', '2026-07-05', 10.00, 3500.00),
(9, 4, '2026-07-04', '2026-07-12', 8.00, 3800.00),

-- Tomate (idIngredient = 5)
(10, 5, '2026-06-28', '2026-07-20', 25.00, 2300.00),
(11, 5, '2026-07-06', '2026-07-25', 20.00, 2500.00),

-- Fromage râpé (idIngredient = 6)
(12, 6, '2026-06-25', '2026-08-10', 12.00, 4500.00),
(13, 6, '2026-07-05', '2026-08-15', 10.00, 4700.00),

-- Saucisse (idIngredient = 7)
(14, 7, '2026-06-30', '2026-07-25', 18.00, 5500.00),
(15, 7, '2026-07-07', '2026-08-01', 15.00, 5700.00),

-- Pâte à pizza (idIngredient = 8)
(16, 8, '2026-06-25', '2026-07-30', 30.00, 1200.00),
(17, 8, '2026-07-05', '2026-08-10', 25.00, 1300.00),

-- Sauce tomate (idIngredient = 9)
(18, 9, '2026-06-28', '2026-08-15', 15.00, 2800.00),
(19, 9, '2026-07-06', '2026-08-20', 12.00, 3000.00),

-- Oignon (idIngredient = 10)
(20, 10, '2026-07-01', '2026-07-28', 10.00, 1800.00),

-- Mayonnaise (idIngredient = 11)
(21, 11, '2026-07-02', '2026-09-01', 8.00, 4200.00),

-- Ketchup (idIngredient = 12)
(22, 12, '2026-07-03', '2026-09-10', 8.00, 3800.00),

-- Moutarde (idIngredient = 13)
(23, 13, '2026-07-04', '2026-09-05', 6.00, 4500.00),

-- Bacon (idIngredient = 14)
(24, 14, '2026-07-01', '2026-07-25', 10.00, 8200.00),

-- Champignon (idIngredient = 15)
(25, 15, '2026-07-02', '2026-07-30', 8.00, 5200.00);

-- ============================================
-- 4. Mouvements des lots d'ingrédients (table mouvementLotIngredient)
-- ============================================
INSERT INTO "mouvementLotIngredient" ("idMouvementLot", "idLot", "idTypeMouvement", "quantite", "dateMouvement") VALUES
-- Lot 1 - Pain burger (idLot = 1)
(1, 1, 1, 50.00, '2026-06-25'), -- Entrée
(2, 1, 2, 5.00, '2026-06-26'),  -- Sortie
(3, 1, 2, 3.00, '2026-06-27'),  -- Sortie
(4, 1, 2, 4.00, '2026-06-28'),  -- Sortie
(5, 1, 2, 3.00, '2026-06-29'),  -- Sortie

-- Lot 2 - Pain burger (idLot = 2)
(6, 2, 1, 30.00, '2026-07-01'), -- Entrée
(7, 2, 2, 2.00, '2026-07-02'),  -- Sortie
(8, 2, 2, 3.00, '2026-07-03'),  -- Sortie

-- Lot 3 - Pain burger (idLot = 3)
(9, 3, 1, 40.00, '2026-07-05'), -- Entrée

-- Lot 4 - Blanc de poulet (idLot = 4)
(10, 4, 1, 15.00, '2026-06-20'), -- Entrée
(11, 4, 2, 1.50, '2026-06-21'), -- Sortie
(12, 4, 2, 2.00, '2026-06-22'), -- Sortie
(13, 4, 2, 1.80, '2026-06-23'), -- Sortie
(14, 4, 2, 1.20, '2026-06-24'), -- Sortie

-- Lot 5 - Blanc de poulet (idLot = 5)
(15, 5, 1, 12.00, '2026-07-02'), -- Entrée

-- Lot 6 - Steak boeuf (idLot = 6)
(16, 6, 1, 20.00, '2026-06-22'), -- Entrée
(17, 6, 2, 2.00, '2026-06-23'), -- Sortie
(18, 6, 2, 1.50, '2026-06-24'), -- Sortie
(19, 6, 2, 2.00, '2026-06-25'), -- Sortie

-- Lot 7 - Steak boeuf (idLot = 7)
(20, 7, 1, 15.00, '2026-07-03'), -- Entrée

-- Lot 8 - Salade verte (idLot = 8)
(21, 8, 1, 10.00, '2026-06-28'), -- Entrée
(22, 8, 2, 1.00, '2026-06-29'), -- Sortie
(23, 8, 2, 2.00, '2026-06-30'), -- Sortie
(24, 8, 2, 1.50, '2026-07-01'), -- Sortie
(25, 8, 2, 0.50, '2026-07-02'), -- Sortie

-- Lot 9 - Salade verte (idLot = 9)
(26, 9, 1, 8.00, '2026-07-04'), -- Entrée

-- Lot 10 - Tomate (idLot = 10)
(27, 10, 1, 25.00, '2026-06-28'), -- Entrée
(28, 10, 2, 1.00, '2026-06-29'), -- Sortie
(29, 10, 2, 2.00, '2026-06-30'), -- Sortie
(30, 10, 2, 1.50, '2026-07-01'), -- Sortie
(31, 10, 2, 0.50, '2026-07-02'), -- Sortie

-- Lot 11 - Tomate (idLot = 11)
(32, 11, 1, 20.00, '2026-07-06'), -- Entrée

-- Lot 12 - Fromage râpé (idLot = 12)
(33, 12, 1, 12.00, '2026-06-25'), -- Entrée
(34, 12, 2, 0.50, '2026-06-26'), -- Sortie
(35, 12, 2, 0.30, '2026-06-27'), -- Sortie
(36, 12, 2, 0.40, '2026-06-28'), -- Sortie

-- Lot 13 - Fromage râpé (idLot = 13)
(37, 13, 1, 10.00, '2026-07-05'), -- Entrée

-- Lot 14 - Saucisse (idLot = 14)
(38, 14, 1, 18.00, '2026-06-30'), -- Entrée
(39, 14, 2, 1.00, '2026-07-01'), -- Sortie
(40, 14, 2, 0.50, '2026-07-02'), -- Sortie

-- Lot 15 - Saucisse (idLot = 15)
(41, 15, 1, 15.00, '2026-07-07'), -- Entrée

-- Lot 16 - Pâte à pizza (idLot = 16)
(42, 16, 1, 30.00, '2026-06-25'), -- Entrée
(43, 16, 2, 2.00, '2026-06-26'), -- Sortie
(44, 16, 2, 1.00, '2026-06-27'), -- Sortie

-- Lot 17 - Pâte à pizza (idLot = 17)
(45, 17, 1, 25.00, '2026-07-05'), -- Entrée

-- Lot 18 - Sauce tomate (idLot = 18)
(46, 18, 1, 15.00, '2026-06-28'), -- Entrée
(47, 18, 2, 0.50, '2026-06-29'), -- Sortie
(48, 18, 2, 0.30, '2026-06-30'), -- Sortie

-- Lot 19 - Sauce tomate (idLot = 19)
(49, 19, 1, 12.00, '2026-07-06'), -- Entrée

-- Lot 20 - Oignon (idLot = 20)
(50, 20, 1, 10.00, '2026-07-01'), -- Entrée

-- Lot 21 - Mayonnaise (idLot = 21)
(51, 21, 1, 8.00, '2026-07-02'), -- Entrée

-- Lot 22 - Ketchup (idLot = 22)
(52, 22, 1, 8.00, '2026-07-03'), -- Entrée

-- Lot 23 - Moutarde (idLot = 23)
(53, 23, 1, 6.00, '2026-07-04'), -- Entrée

-- Lot 24 - Bacon (idLot = 24)
(54, 24, 1, 10.00, '2026-07-01'), -- Entrée
(55, 24, 2, 0.50, '2026-07-02'), -- Sortie

-- Lot 25 - Champignon (idLot = 25)
(56, 25, 1, 8.00, '2026-07-02'), -- Entrée
(57, 25, 2, 0.30, '2026-07-03'); -- Sortie

-- ============================================
-- 5. Équipements (table equipement)
-- ============================================
INSERT INTO "equipement" ("idEquipement", "nomEquipement", "idTypeEquipement", "idMethodeComptable", "prixUnitaire", "quantiteMin") VALUES
(1, 'Barquette sandwich', 1, 2, 50.00, 100.00),
(2, 'Sachet burger', 1, 2, 30.00, 100.00),
(3, 'Cuillère en bois', 2, 1, 100.00, 50.00),
(4, 'Fourchette', 2, 1, 80.00, 50.00),
(5, 'Réfrigérateur portable', 3, 2, 1500000.00, 1.00),
(6, 'Machine à café', 4, 2, 800000.00, 1.00),
(7, 'Plaque chauffante', 4, 2, 1200000.00, 1.00),
(8, 'Assiette carton', 1, 1, 25.00, 200.00),
(9, 'Verre en plastique', 1, 1, 15.00, 200.00),
(10, 'Congélateur', 3, 2, 2000000.00, 1.00);

-- ============================================
-- 6. Mouvements des équipements (table mouvementEquipement)
-- ============================================
INSERT INTO "mouvementEquipement" ("idMouvementEquipement", "idTypeMouvement", "idEquipement", "quantite", "dateMouvement") VALUES
-- Barquette sandwich (idEquipement = 1)
(1, 1, 1, 200.00, '2026-06-20'),
(2, 2, 1, 50.00, '2026-06-25'),
(3, 2, 1, 30.00, '2026-06-27'),
(4, 2, 1, 20.00, '2026-06-29'),
(5, 1, 1, 100.00, '2026-07-01'),

-- Sachet burger (idEquipement = 2)
(6, 1, 2, 150.00, '2026-06-22'),
(7, 2, 2, 40.00, '2026-06-26'),
(8, 2, 2, 25.00, '2026-06-28'),
(9, 2, 2, 15.00, '2026-06-30'),
(10, 1, 2, 100.00, '2026-07-03'),

-- Cuillère en bois (idEquipement = 3)
(11, 1, 3, 50.00, '2026-06-24'),
(12, 2, 3, 15.00, '2026-06-27'),
(13, 2, 3, 10.00, '2026-06-30'),
(14, 1, 3, 30.00, '2026-07-02'),

-- Fourchette (idEquipement = 4)
(15, 1, 4, 50.00, '2026-06-24'),
(16, 2, 4, 20.00, '2026-06-28'),
(17, 2, 4, 10.00, '2026-07-01'),
(18, 1, 4, 30.00, '2026-07-04'),

-- Assiette carton (idEquipement = 8)
(19, 1, 8, 300.00, '2026-06-25'),
(20, 2, 8, 80.00, '2026-06-27'),
(21, 2, 8, 60.00, '2026-06-29'),
(22, 2, 8, 40.00, '2026-07-01'),
(23, 1, 8, 200.00, '2026-07-05'),

-- Verre en plastique (idEquipement = 9)
(24, 1, 9, 250.00, '2026-06-26'),
(25, 2, 9, 60.00, '2026-06-29'),
(26, 2, 9, 40.00, '2026-07-02'),
(27, 1, 9, 150.00, '2026-07-06'),

-- Réfrigérateur portable (idEquipement = 5)
(28, 1, 5, 1.00, '2026-06-15'),

-- Machine à café (idEquipement = 6)
(29, 1, 6, 1.00, '2026-06-18'),

-- Plaque chauffante (idEquipement = 7)
(30, 1, 7, 1.00, '2026-06-20'),

-- Congélateur (idEquipement = 10)
(31, 1, 10, 1.00, '2026-06-22');

-- ============================================
-- 7. Vérifications
-- ============================================

-- Voir les ingrédients
SELECT * FROM "ingredient" ORDER BY "idIngredient";

-- Voir les lots avec leurs ingrédients
SELECT 
    l."idLot",
    i."nomIngredient",
    l."quantiteInitiale",
    l."prixAchatUnitaire",
    l."dateReception",
    l."datePeremption"
FROM "lotIngredient" l
JOIN "ingredient" i ON i."idIngredient" = l."idIngredient"
ORDER BY l."idLot";

-- Voir les mouvements des lots
SELECT 
    m."idMouvementLot",
    i."nomIngredient",
    l."idLot",
    t."libelle" as type_mouvement,
    m."quantite",
    m."dateMouvement"
FROM "mouvementLotIngredient" m
JOIN "lotIngredient" l ON l."idLot" = m."idLot"
JOIN "ingredient" i ON i."idIngredient" = l."idIngredient"
JOIN "typeMouvement" t ON t."idTypeMouvement" = m."idTypeMouvement"
ORDER BY m."idMouvementLot";

-- Voir les mouvements des équipements
SELECT 
    m."idMouvementEquipement",
    e."nomEquipement",
    t."libelle" as type_mouvement,
    m."quantite",
    m."dateMouvement"
FROM "mouvementEquipement" m
JOIN "equipement" e ON e."idEquipement" = m."idEquipement"
JOIN "typeMouvement" t ON t."idTypeMouvement" = m."idTypeMouvement"
ORDER BY m."idMouvementEquipement";

-- ============================================
-- 8. Statistiques des lots
-- ============================================

-- Quantité totale par ingrédient
SELECT 
    i."nomIngredient",
    COALESCE(SUM(l."quantiteInitiale"), 0) as quantite_totale
FROM "ingredient" i
LEFT JOIN "lotIngredient" l ON l."idIngredient" = i."idIngredient"
GROUP BY i."idIngredient", i."nomIngredient"
ORDER BY i."nomIngredient";

-- Lots en alerte (quantiteInitiale < seuil)
SELECT 
    i."nomIngredient",
    l."quantiteInitiale",
    i."seuilAlerteQuantite",
    CASE 
        WHEN l."quantiteInitiale" < i."seuilAlerteQuantite" THEN 'ALERTE'
        ELSE 'OK'
    END as statut
FROM "lotIngredient" l
JOIN "ingredient" i ON i."idIngredient" = l."idIngredient"
WHERE l."quantiteInitiale" < i."seuilAlerteQuantite"
ORDER BY i."nomIngredient";