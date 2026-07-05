# Sprint 3 - Rattrapage:

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
        Affichage:
            - dans fragments/sidebar.jsp / nouvelles sections :
                - liste des Ingredients bientot perimés
                - liste des ingredients bientot perimés par idIngredient
            - dans ingredient/
                - ingredient-bientot-perimes.jsp
                - ingredient-bientot-perimes-by-idIngredient.jsp

    - Liste des ingredients perimes ( Date perumption < Date Now())
    - Calcul automatique des pertes dues au perumptions : SUM ( quantite * prix ) pour les ingredients dans le stock dont ****
    - Filtre generalisé:
        * par date de permumptions
        * par ingredient

- 2- Alertes:
    - Detection automatique d'un stock faible 
        CREATE TABLE "ingredient" (
            "idIngredient" SERIAL PRIMARY KEY,
            "nomIngredient" VARCHAR(100) NOT NULL,
            "seuilAlerteQuantite" NUMERIC(10, 2) NOT NULL,
            "uniteMesure" VARCHAR(20) NOT NULL
        );

        CREATE TABLE "lotIngredient" (
            "idLot" SERIAL PRIMARY KEY,
            "idIngredient" INT NOT NULL,
            "dateReception" DATE NOT NULL,
            "datePeremption" DATE NOT NULL,
            "quantiteInitiale" NUMERIC(10, 2) NOT NULL,
            "quantiteRestante" NUMERIC(10, 2) NOT NULL,
            "prixAchatUnitaire" NUMERIC(10, 2) NOT NULL,
            FOREIGN KEY ("idIngredient") REFERENCES "ingredient"("idIngredient")
        );
        -> il n'y a pas de quantiteMin pour reference lors de gestion d'alertes selon la quantite dans le stock pour les ingredients
            - Donc on referencier juste que si quantitetotal d'un ingredient < 20 , -> retourne la lsite des ingredients en alerte 
            **** deja faites ( voir sprint-1-lotIngredeinet-Hasina )
    - Detection automatiquement une perumption proche
        **** deja faite ci-dessus 
    - Detecter les ruptures de stock 
        ????
    - Afficher les alertes sur le tableau de bord 
        - Liste des alertes 

- 3- Gestion des equipements:
    - Demande d'achat equipement 
        **** deja faites ( sprint2-GestionEquipement-Hasina)
    - Gestion des stokc 
        **** deja faites 

- 4- Calcul des pertes financieres:
    - Valoriser les produits perimes
    - Valoriser les equipements remplaces 
    - Calculer les pertes globales

Pertes financiers = Somme des produits qui ne sont pas vendues + Somme des equipements remplaces + somme des ingredients permies


## qustions:
- 1- Ahoana ny ahafantarana ny isan produits actuel ao amintsika ?
