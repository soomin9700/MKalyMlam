-- Création du type ENUM pour l'action
CREATE TYPE action_personnalisation AS ENUM ('AJOUTER', 'RETIRER');

-- Création de la table
CREATE TABLE personnalisation_commande (
    id_personnalisation BIGSERIAL PRIMARY KEY,
    id_ligne BIGINT NOT NULL,
    id_ingredient INTEGER NOT NULL,
    action action_personnalisation NOT NULL,
    quantite_ajustee NUMERIC(10,2) NOT NULL,
    date_creation TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_personnalisation_ligne
        FOREIGN KEY (id_ligne) REFERENCES ligne_commande(id) ON DELETE CASCADE,

    CONSTRAINT fk_personnalisation_ingredient
        FOREIGN KEY (id_ingredient) REFERENCES ingredient(idingredient) ON DELETE RESTRICT
);

-- Index pour accélérer les recherches par ligne de commande
CREATE INDEX idx_personnalisation_ligne ON personnalisation_commande(id_ligne);

-- Ex na ex na ex na
SELECT c.idcommande, l.idline, i.nomingredient, p.quantiteajustee
FROM commande c
JOIN ligne_commande l ON c.idcommande = l.idcommande
JOIN personnalisation_commande p ON l.idline = p.idline
JOIN ingredient i ON p.idingredient = i.idingredient
WHERE c.idcommande = 45;