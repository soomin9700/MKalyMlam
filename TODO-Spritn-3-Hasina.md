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
    - Somme des equipement remplaces: 

## qustions:
- 1- Ahoana ny ahafantarana ny isan produits actuel ao amintsika ?
