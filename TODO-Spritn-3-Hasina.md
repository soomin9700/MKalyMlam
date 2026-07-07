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

## qustions:
- 1- Ahoana ny ahafantarana ny isan produits actuel ao amintsika ?


foodTruckDb=# select * from "lotIngredient";
 idLot | idIngredient | dateReception | datePeremption | quantiteInitiale | prixAchatUnitaire | idTypeMouvement 
-------+--------------+---------------+----------------+------------------+-------------------+-----------------            
     1 |            1 | 2026-07-05    | 2026-07-06     |            20.00 |           5000.00 |                
     2 |            1 | 2026-07-06    | 2026-07-24     |           10.00 |           4000.00 |               1
     3 |            1 | 2026-07-06    | 2026-07-07     |           15.00 |              1.00 |               2


On a comme suit dans la table lotIngredient:
OBJECTIFS:
    1- On obtient les ingredients perimes ( nom , montnant = sommme quantite initale * prix unitaire )
    2- il n'y a pas de donnees effacees
    3- historique pour chaque mouvement des ingredients 

exemple
- Fonctionnement:
    - On fait une ENTREE de 10 fromages de 1000 Ar le 1 Juillet 2026, date de perumption: 5 Juillet 2026
    - On fait une SORTIE de 8 fromages de 1000 Ar le 4 juillet 2026, 
-> Donc , apres la sortie de 8 fromages de 4 juillet 2026, il reste 2 fromages de 1000 Ar, date de perumption: 5 Juillet 2026
   Alors, le 5 juillet 2026, on a donc  2 fromages  de 1000 Ar restants , date de perumption: 5 Juillet 2026, Ces 2 formages ne sont pas enocre perimes, on peut encore faire SORITE DE CES 2 fromages le jour de 5 juillet , mais on ne le fait pas
   - Nous sommes maintenant 6 juillet 2026, donc ces 2 fromages sont perimes, et on a donc une perte de 2000 Ar ( 2 * 1000 Ar ) pour le 6 juillet 2026

   - Normalement, on doit avoir un tableau ingredientsPerimes avec les colonnes suivantes:
        - idIngredient
        - nomIngredient
        - quantitePerime
        - prixAchatUnitaire
        - montantPerte = quantitePerime * prixAchatUnitaire
    1- Si on est 6 juillet 2026, donc 
    Comment va t-on gerer les ingredients perimes dans le stock 