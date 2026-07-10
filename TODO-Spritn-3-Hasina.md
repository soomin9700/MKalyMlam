# Sprint 3 - Rattrapage: TODOLISTb-Hasina

===================================
## Fonctionnalites attendues:
- 1- Modules 'ingredients':
    - [OK] Liste des ingredients dont la date la date de perumption - date now() >= 0 and <=3 
        [ok] backend:
            - LotIngredientController.java:
                - GetMapping("/ingredients/bientot-perimes") 
                - getIngredientsBientotPerimes() 
                - GetMapping("/ingredients/bientot-perimes/{idIngredient}")
                - getIngredientsBientotPerimesByIdIngredient(Long idIngredient)
            - LotIngredientService.java:
                - getIngredientsBientotPerimes() 
                    - retourne la lsite des lotingredients dont la date de perumption - date now() >= 0 and <=3
                - getIngredientsBientotPerimesByIdIngredient(Long idIngredient) 
                    - retourne la lsite des lotingredients dont la date de perumption - date now() >= 0 and <=3 et idIngredient = idIngredient
            - LotIngredientRepository.java:
                - findByDatePeremptionBetween(Date startDate, Date endDate) 
                    - retourne la lsite des lotingredients dont la date de perumption - date now() >= 0 and <=3
                - findByDatePeremptionBetweenAndIdIngredient(Date startDate, Date endDate, Long idIngredient) 
                    - retourne la lsite des lotingredients dont la date de perumption - date now() >= 0 and <=3 et idIngredient = idIngredient
        [ok]Affichage:
            - dans fragments/sidebar.jsp / nouvelles sections :
                - liste des Ingredients bientot perimés
                - liste des ingredients bientot perimés par idIngredient
            - dans ingredient/
                - ingredient-bientot-perimes.jsp
                - ingredient-bientot-perimes-by-idIngredient.jsp

    - [ok] Liste des ingredients perimes ( Date perumption < Date Now())

    - [ok] Calcul automatique des pertes dues au perumptions : SUM ( quantite * prix ) pour les ingredients dans le stock dont la date de perumption < Date Now() 
        - [ok] backend:
            - LotIngredientController.java:
                - GetMapping("/ingredients/perimes") 
                - getIngredientsPerimes() 
            - LotIngredientService.java:
                - getIngredientsPerimes() 
                    - retourne la lsite des lotingredients dont la date de perumption < Date Now()
            - LotIngredientRepository.java:
                - findByDatePeremptionBefore(Date date) 
                    - retourne la lsite des lotingredients dont la date de perumption < Date Now()
        - [ok]Affichage:
            - dans ingredient/
                - ingredient-perimes.jsp

    - [ok] Filtre generalisé:
        * par date de permumptions
        * par ingredient

- 2- Alertes:
    - [ok] Detection automatique d'un stock faible:
            **** deja faites ( voir sprint-1-lotIngredient-Hasina )
  
    - [ok] Detection automatiquement une perumption proche
        **** deja faite ci-dessus 
    - Detecter les ruptures de stock 
        ????
    - [ok] Afficher les alertes sur le tableau de bord 
        - Liste des alertes : les ingredients en alertes

- 3- Gestion des equipements:
    - [ok] Demande d'achat equipement 
        **** deja faites ( sprint2-GestionEquipement-Hasina)
    - [ok] Gestion des stokc 
        **** deja faites 

- 4- Calcul des pertes financieres:
    - Valoriser les produits perimes
    - Valoriser les equipements remplaces 
    - Calculer les pertes globales

Pertes financiers = Somme des produits qui ne sont pas vendues + Somme des equipements remplaces + somme des ingredients permies
    - Somme des produits qui ne sont pas vendues : 0 car on fabrique un produit seuleent lorsqu'il y a une commande
    - Somme des equipement remplaces: pas d'equipement remplaces pour le moment
    - Somme des ingredients perimes: 

=======================================================================================================================
# RESTRUCTURATION ET REPERAGE A FAIRE POUR LE SPRINT 3
## LotIngredients bientot perimes:
    - Table: lotIngredient
    - principe: 
        - retourne la liste des lotIngredients dont la date de perumption - date now() >= 0 and <=3
        - quantite restante > 0 
        - objectif, avoir:
            - Nom de l'ingredient 
            - sa quantite restante
            - sa date de peremption

## LotIngredient perimes:
    - Table: lotIngredient
    - principe:
        - retourne la liste des lotIngredients dont la date de peremption < date now()
        - quantite restante > 0
        - objectif, avoir:
            - liste des ingredients perimes avec les informations suivantes:
                - Nom de l'ingredient 
                - sa quantite restante
                - sa date de peremption
                - son prix unitaire
            - montant perte dues aux peremptions = quantite restante * prix unitaire
            

## Mouvement des ingredinets:
    - Table: 
        - ingredient
        - lotIngredient
        - mouvementLotIngredient
    - principe:
        - Sortie des ingredients dans un lotIngredient impossible Si :
            - date de peremption < date now()
            - quantite restante = 0
        - Si on fait une sortie d'un ingredient dans le stock:
            1- On choisi un lot ( idLot_)
                - verifie si ce lot n'est pas perimé
                - verifie si quantite restante > 0
            2- verifie si quantite pour la sortie <= quantite restante de lot choisi
        
## Mouvement des equipements:
    - Table: 
        - equipement
        - mouvementEquipement
    - principe:
        -( A ajouter ) Sortie d'un equipement impossible Si :
            - quantite restante = 0
        - Si on fait une sortie d'un equipement dans le stock:
            1- On choisi un equipement ( idEquipement)
                - verifie si quantite restante > 0
            2- verifie si quantite pour la sortie <= quantite restante de l'equipement choisi


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
    "quantiteInitiale" NUMERIC(10,2) NOT NULL,
    "prixAchatUnitaire" NUMERIC(10,2) NOT NULL,

    FOREIGN KEY ("idIngredient")
        REFERENCES "ingredient"("idIngredient")
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
