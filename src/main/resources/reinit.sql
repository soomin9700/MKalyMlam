-- Supprimer la table si elle existe
DROP TABLE IF EXISTS ingredient CASCADE;

-- Créer la table avec les colonnes camelCase
CREATE TABLE ingredient (
    idIngredient SERIAL PRIMARY KEY,
    nomIngredient VARCHAR(100) NOT NULL,
    seuilAlerteQuantite NUMERIC(10, 2) NOT NULL,
    uniteMesure VARCHAR(20) NOT NULL
);

-- Insérer des données de test
INSERT INTO ingredient (nomIngredient, seuilAlerteQuantite, uniteMesure) VALUES
('Farine de blé', 5.0, 'kg'),
('Sucre cristallisé', 3.0, 'kg'),
('Sel fin', 1.0, 'kg'),
('Beurre doux', 2.5, 'kg'),
('Œufs frais', 12.0, 'unité');


-- Voir les doublons
SELECT "libelle", count(*) FROM "role" GROUP BY "libelle" HAVING count(*) > 1;

-- Supprimer les doublons en gardant le plus petit idRole pour chaque libelle
DELETE FROM "role" a USING "role" b
WHERE a."idRole" > b."idRole" AND a."libelle" = b."libelle";

-- Ajouter une contrainte UNIQUE sur libelle pour éviter que ça se reproduise
ALTER TABLE "role" ADD CONSTRAINT unique_role_libelle UNIQUE ("libelle");




CREATE TABLE employe (
    id SERIAL PRIMARY KEY,
    matricule VARCHAR(50) UNIQUE NOT NULL,
    "idUtilisateur" INT NOT NULL UNIQUE,
    poste VARCHAR(100),
    departement VARCHAR(100),
    "dateEmbauche" DATE,
    "salaireBase" NUMERIC(12,2),
    FOREIGN KEY ("idUtilisateur") REFERENCES utilisateur("idUtilisateur")
);

CREATE TABLE conge (
    id SERIAL PRIMARY KEY,
    "idEmploye" INT NOT NULL,
    "dateDebut" DATE NOT NULL,
    "dateFin" DATE NOT NULL,
    "typeConge" VARCHAR(50),
    "statut" VARCHAR(50) DEFAULT 'EN_ATTENTE',
    FOREIGN KEY ("idEmploye") REFERENCES employe(id)
);

CREATE TABLE absence (
    id SERIAL PRIMARY KEY,
    "idEmploye" INT NOT NULL,
    "dateAbsence" DATE NOT NULL,
    motif VARCHAR(255),
    justifiee BOOLEAN DEFAULT FALSE,
    commentaire TEXT,
    FOREIGN KEY ("idEmploye") REFERENCES employe(id)
);