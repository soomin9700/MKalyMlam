## Inventaire journalier

> **Assigné à : Nampoina**

### Entités concernées

* `Inventaire_Journalier`

### Fonctionnalités à implémenter

En fin de chaque journée de travail, permettre de faire un inventaire physique :

* Comparer ce qui est physiquement en stock avec ce que le système pense qu'il y a
* Enregistrer les écarts (pertes, surplus)
* L'inventaire peut porter sur des **ingrédients** ou des **équipements**

Exemple concret : le système dit qu'il reste 500g de fromage, mais physiquement on ne compte que 350g → écart de -150g, probablement une perte ou une erreur de saisie à corriger.

### Type d'item inventorié

* `INGREDIENT`
* `EQUIPEMENT`

---

## Fichiers à créer

### InventaireJournalier

* `InventaireJournalier.java`
* `InventaireJournalierRepository.java`
* `InventaireJournalierService.java`
* `InventaireJournalierController.java`

---

## Structure de l'entité

### Champs

* `id_session` → FK vers `SessionTruck`
* `date_inventaire` → LocalDate
* `type_item` → enum (`INGREDIENT`, `EQUIPEMENT`)
* `id_item` → Long (ID de l'ingrédient ou de l'équipement concerné)
* `quantite_physique_constatee` → Double (ce qu'on a compté physiquement)
* `quantite_theorique_systeme` → Double (ce que le système indique)
* `ecart_inventaire` → Double (calculé automatiquement : physique - théorique)

Exemple :

```java
@ManyToOne
@JoinColumn(name = "id_session")
private SessionTruck sessionTruck;

private LocalDate dateInventaire;

@Enumerated(EnumType.STRING)
private TypeItem typeItem;

private Long idItem;

private Double quantitePhysiqueConstatee;

private Double quantiteTheoriqueSysteme;

private Double ecartInventaire;
```

---

## Endpoints à créer

* `/inventaire/save`
* `/inventaire/findBySession` — tous les inventaires d'une session
* `/inventaire/findAll`
* `/inventaire/ecarts` — retourner uniquement les lignes avec un écart non nul

---

## Affichage minimal

### Formulaire de saisie inventaire

Champs :

* Session du jour (dropdown)
* Type d'item (INGREDIENT ou EQUIPEMENT)
* Item concerné (dropdown filtré selon le type choisi)
* Quantité physique constatée (input numérique)

Action :

Bouton **Enregistrer**

Effet :

* La quantité théorique est récupérée automatiquement depuis le système
* L'écart est calculé automatiquement

### Tableau des inventaires

Afficher :

* Item
* Quantité théorique
* Quantité physique
* Écart

Exemple :

```text
Fromage   | 500g | 350g | -150g ⚠️
Emballage | 200  | 210  | +10
Poulet    | 800g | 800g | 0 ✅
```

---


# Liste TODO (3h zao + 2h) 8:30
- manao todo

- BASE
    - tables concernées: Module 3 manontolo
        - "ingredient"
        - "lotIngredient"
        - "mouvementLotIngredient" -> A CRÉER

        - "equipement"
        - "typeMouvement"
        - "mouvementEquipement"

        - "inventaireJournalier"

    - création de script réinit de ces tables (reinit_module_3)
    - données de test

- BACK
    * `mouvementIngredient.java`
    * `TypeItem.java`
    * `InventaireJournalier.java`
        * `id_session` → FK vers `SessionTruck`
        * `date_inventaire` → LocalDate
        * `type_item` → enum (`INGREDIENT`, `EQUIPEMENT`)
        * `id_item` → Long (ID de l'ingrédient ou de l'équipement concerné)
        * `quantite_physique_constatee` → Double (ce qu'on a compté physiquement)
        * `quantite_theorique_systeme` → Double (ce que le système indique) 
            -> avy ao am "quantiteRestante" ao am table "lotIngredient"
        * `ecart_inventaire` → Double (calculé automatiquement : physique - théorique)
    * `InventaireJournalierRepository.java`
    * `InventaireJournalierService.java`
        - functions
            - save
            - update
            - findAll
            - findByID
            - findBy
                - nomIngredient ou nomEquipement
                - ingredient(lot) ou equipement
            - sortByEcart
    * `InventaireJournalierController.java`
        * `/inventaire/save`
        * `/inventaire/findBySession` — tous les inventaires d'une session
        * `/inventaire/findAll`
        * `/inventaire/ecarts` — retourner uniquement les lignes avec un écart non nul

- FRONT
    - ajout du choix Inventaire dans le sidebar 
    - form saisie inventaire
        * Session du jour (dropdown)
        * Type d'item (INGREDIENT ou EQUIPEMENT)
        * Item concerné (dropdown filtré selon le type choisi)
        * Quantité physique constatée (input numérique)
        [ENREGISTRER]
            -> Effet :
            * La quantité théorique est récupérée automatiquement depuis le système
            * L'écart est calculé automatiquement

    - tableau des inventaires
        | Item     | Quantité théorique | Quantité physique | Écart     |
        |Fromage   | 500g               | 350g              | -150g ⚠️  |
        |Emballage | 200                | 210               | +10       | 
        |Poulet    | 800g               | 800g              | 0 ✅      |
        - + filtre et tri
    
    - style CSS à integrer


