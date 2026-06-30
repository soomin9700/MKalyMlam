# REMARQUES : CHAQUE STATISTIQUES (/ LISTES / FACTURES .... ) DOIT POUVOIR ETRE EXPORTE EN CSV,PDF( PAS ENCORE D'IMPORT); EN CAS DE QUESTION , POSEZ VOS QUESTIONS AU CP(ZA MO ZANY LE IZY)

# Tâches à accomplir

## Journée de travail pour le truck

> **Back-end assigné : Loïc**
> **Front-end assigné : Nampoina** — *Integration côté interface*

### Entités concernées

* `SessionTruck`
* `Truck`

### Fonctionnalités à implémenter

* Définir quel chauffeur prend quel camion.
* Définir l'itinéraire à suivre par le truck.
* Définir l'argent de départ dans le truck (**fond de caisse / monnaie**).
* Définir l'argent restant dans la caisse lors de la clôture de la journée.
* Gérer le statut du camion :

  * `DISPONIBLE`
  * `EN_REPARATION`
  * `EN_PANNE`

---

## Fichiers à créer

### Truck

* `Truck.java`
* `TruckRepository.java`
* `TruckService.java`
* `TruckController.java`

### Endpoints Truck

* `/truck/save`
* `/truck/update`
* `/truck/delete`
* `/truck/find`
* `/truck/findAll`
* `/truck/disponibles` *(endpoint utile pour récupérer uniquement les trucks disponibles)*

---

### SessionTruck

* `SessionTruck.java`
* `SessionTruckRepository.java`
* `SessionTruckService.java`
* `SessionTruckController.java`

### Endpoints SessionTruck

* `/session/ouvrir`

  * Crée une nouvelle session avec le statut `OUVERTE`.

* `/session/cloturer`

  * Passe la session au statut `CLOTUREE`.
  * Permet de saisir le fond de caisse de clôture.

---

## Affichage minimal

### Formulaire : **Ouvrir une session**

Champs :

* Sélection d'un **truck** (dropdown filtré sur les trucks avec statut `DISPONIBLE`)
* Sélection d'un **itinéraire** (dropdown existant)
* Saisie du **fond de caisse de départ**

Action :

* Bouton **Démarrer la journée**

---

### Liste des sessions du jour

Afficher :

* Truck associé
* Chauffeur
* Itinéraire
* Statut
* Fond de caisse de départ

Action :

* Bouton **Clôturer** pour terminer la session

# Tâches à accomplir

## Relier commande avec session truck

>  **Back-end assigné : Fifa**
>  **Front-end assigné : Fabrice** — *Integration côté interface*

### Entités concernées

* `Commande`

### Fonctionnalités à implémenter

* Relier chaque vente/commande à une **session truck** (`SessionTruck`) afin qu'une commande appartienne à une journée de travail précise.
* Ajouter la gestion des différents statuts d'une commande.

### Statuts possibles (`statut_commande`)

* `EN_ATTENTE`
* `PREPARATION`
* `PRETE_POUR_RECUPERATION`
* `LIVREE`
* `ANNULEE`

### Types de commande (`type_commande`)

* `SUR_PLACE`
* `A_DISTANCE`

---

## Fichiers à modifier

### Commande

Modifier :

* `Commande.java`

Ajouts :

* Champ `id_session` en clé étrangère vers `SessionTruck`
* Enum `statut_commande`
* Enum `type_commande`

Exemple :

```java
@ManyToOne
@JoinColumn(name = "id_session")
private SessionTruck sessionTruck;

@Enumerated(EnumType.STRING)
private StatutCommande statutCommande;

@Enumerated(EnumType.STRING)
private TypeCommande typeCommande;
```

---

## Services à enrichir

Pas besoin de créer un nouveau :

* `CommandeRepository.java`
* `CommandeService.java`

Ajouter uniquement :

* Nouvelle méthode dans `VenteService.java`

Exemple :

* Associer automatiquement la commande à la session ouverte du truck
* Gérer les changements de statut

---

## Controller à modifier

### CommandeController

Ajouter un nouvel endpoint :

* `/commande/changerStatut`

Paramètres :

* `id_commande`
* `nouveau_statut`

Exemple :

```http
POST /commande/changerStatut?id_commande=1&nouveau_statut=PREPARATION
```

---

## Affichage minimal

### Tableau des commandes

Ajouter :

* Une colonne **Statut**
* Un dropdown dans chaque ligne pour modifier le statut de la commande

Exemple :

* EN_ATTENTE
* PREPARATION
* PRETE_POUR_RECUPERATION
* LIVREE
* ANNULEE

---

### Filtres

Ajouter un filtre en haut du tableau :

Exemples :

* Toutes
* EN_ATTENTE
* PREPARATION
* LIVREE

Permet de filtrer rapidement les commandes selon leur statut.

---

### Création d'une commande

Ajouter un champ de sélection :

Options :

* `SUR_PLACE`
* `A_DISTANCE`

Format possible :

* Boutons radio
  ou
* Dropdown

Permet de distinguer immédiatement le mode de consommation de la commande.

# Tâches à accomplir

## Personnalisation d'un plat

>  **Back-end assigné : i Aina**
>  **Front-end assigné : Giovan** — *Integration côté interface*

### Entités concernées

* `PersonnalisationCommande`

### Fonctionnalités à implémenter

Permettre la personnalisation d'un plat lors de la commande :

* Ajouter un ingrédient
* Retirer un ingrédient
* Ajuster la quantité d'un ingrédient
* Conserver l'historique des personnalisations par ligne de commande

### Actions possibles (`action`)

* `AJOUTER`
* `RETIRER`

---

## Fichiers à créer

### PersonnalisationCommande

Créer :

* `PersonnalisationCommande.java`
* `PersonnalisationCommandeRepository.java`
* `PersonnalisationCommandeService.java`
* `PersonnalisationCommandeController.java`

---

## Structure de l'entité

### Champs à ajouter

* `id_ligne` → clé étrangère vers `LigneCommande`
* `id_ingredient` → clé étrangère vers `Ingredient`
* `action` → enum (`AJOUTER`, `RETIRER`)
* `quantite_ajustee`

Exemple :

```java
@ManyToOne
@JoinColumn(name = "id_ligne")
private LigneCommande ligneCommande;

@ManyToOne
@JoinColumn(name = "id_ingredient")
private Ingredient ingredient;

@Enumerated(EnumType.STRING)
private ActionPersonnalisation action;

private Integer quantiteAjustee;
```

---

## Endpoints à créer

### CRUD standard

* `/personnalisation/save`
* `/personnalisation/update`
* `/personnalisation/delete`
* `/personnalisation/find`

### Endpoint utile

* `/personnalisation/findAllByLigne`

Paramètre :

* `id_ligne`

Permet de récupérer toutes les personnalisations liées à une ligne de commande.

---

## Affichage minimal

### Page de commande de Fifa

Pour chaque ligne de produit :

Ajouter :

#### Sélection d'ingrédient

Dropdown contenant la liste des ingrédients disponibles.

Exemple :

* Fromage
* Oignon
* Sauce piquante
* Bacon

---

#### Sélection d'action

Dropdown :

* AJOUTER
* RETIRER

---

#### Champ quantité

Input numérique :

Exemple :

* `1`
* `2`
* `3`

---

#### Bouton d'action

Bouton :

**Personnaliser**

Action :

* Enregistre la personnalisation

---

## Affichage des personnalisations

Sous chaque ligne concernée, afficher la liste des personnalisations appliquées.

Exemple :

```text
Burger Classic
+ Bacon x2
- Oignon x1
+ Sauce piquante x1
```

Cela permet de visualiser clairement toutes les modifications apportées au plat.

# Tâches à accomplir

## Alerte stock et gestion des lots d'ingrédients

>  **Back-end assigné : Hasina**
>  **Front-end assigné : Nampoina** — *Integration côté interface*

### Entités concernées

* `LotIngredient`

### Fonctionnalités à implémenter

Permettre la gestion des arrivages de stock :

* Enregistrer chaque nouvel arrivage d'un ingrédient
* Enregistrer la date de réception (achat)
* Enregistrer la date de péremption
* Enregistrer la quantité initiale reçue
* Suivre la quantité restante au fur et à mesure des utilisations
* Mettre à jour dynamiquement le stock lors des ventes

Mettre en place un système d'alerte :

* Vérifier automatiquement si un ingrédient passe sous son seuil minimal
* Déclencher une alerte lorsqu'il reste peu de stock

Exemple :

```text
Poulet : 20g restants (seuil = 50g) → ALERTE
```

---

## Fichiers à créer

### LotIngredient

Créer :

* `LotIngredient.java`
* `LotIngredientRepository.java`
* `LotIngredientService.java`
* `LotIngredientController.java`

---

## Structure de l'entité

### Champs à ajouter

* `id_ingredient` → clé étrangère vers `Ingredient`
* `date_reception`
* `date_peremption`
* `quantite_initiale`
* `quantite_restante`

Exemple :

```java
@ManyToOne
@JoinColumn(name = "id_ingredient")
private Ingredient ingredient;

private LocalDate dateReception;

private LocalDate datePeremption;

private Double quantiteInitiale;

private Double quantiteRestante;
```

---

## Services à implémenter

### Vérification des alertes

Dans `LotIngredientService.java`

Ajouter :

```java
verifierAlerte(Long idIngredient)
```

Rôle :

* Récupérer la quantité restante totale de l'ingrédient
* Comparer avec `seuil_alerte_quantite` déjà existant dans `Ingredient`
* Retourner une alerte si le stock est insuffisant

Logique :

```text
si quantite_restante <= seuil_alerte_quantite
→ ALERTE
```

---

## Mise à jour dynamique du stock

À chaque utilisation d'un ingrédient :

* Déduire la quantité utilisée du lot le plus ancien (FIFO)
* Passer au lot suivant si le premier lot est épuisé

Exemple :

```text
Lot 1 : 100g poulet
Utilisation : 30g
Reste : 70g
```

---

## Endpoints à créer

### CRUD standard

* `/lot/save`
* `/lot/update`
* `/lot/delete`
* `/lot/find`
* `/lot/findAll`

### Endpoint utile

* `/lot/alertes`

Rôle :
Retourner tous les ingrédients dont le stock est sous le seuil d'alerte.

---

## Affichage minimal

### Tableau des lots

Afficher :

* Ingrédient
* Date de réception
* Date de péremption
* Quantité initiale
* Quantité restante
* Statut

Exemple :

```text
Poulet | 2026-06-20 | 2026-06-28 | 100g | 20g | ALERTE
Farine | 2026-06-21 | 2026-07-15 | 200g | 150g | OK
```

---

### Alerte visuelle

Si :

```text
quantite_restante < seuil_alerte_quantite
```

Afficher :

* Ligne en rouge
  ou
* Texte : **ALERTE**

---

## Formulaire : Nouvel arrivage

Champs :

* Choisir l'ingrédient (dropdown)
* Saisir la quantité reçue
* Saisir la date de péremption

Action :

Bouton :

**Enregistrer l'arrivage**

Effet :

* Création d'un nouveau lot
* Initialisation automatique :

  * `date_reception = aujourd'hui`
  * `quantite_restante = quantite_initiale`

# Tâches à accomplir

## Gestion du profil employé et des congés

>  **Back-end assigné : Aro** *(point délicat — le recalcul de salaire selon le type d'absence [Manotania ani Hasina])*
>  **Front-end assigné : Ismael** — *tIntegration côté interface*

### Entités concernées

* `Utilisateur`
* `AbsenceConge`

---

## Fonctionnalités à implémenter

### Gestion des employés

Permettre :

* Afficher la liste des employés
* Afficher leur rôle
* Afficher leur salaire de base
* Gérer leur statut actif/inactif

Règle métier :

* En cas de renvoi ou départ d'un employé :

  * ne jamais supprimer l'utilisateur de la base
  * simplement mettre :

```text
statut_actif = false
```

Cela permet de conserver l'historique.

---

### Gestion des congés et absences

Permettre à un employé :

* Faire une demande de congé ou absence
* Choisir un type d'absence
* Choisir une date de début
* Choisir une date de fin

Permettre à l'administrateur :

* Voir les demandes en attente
* Valider une demande
* Refuser une demande

---

### Recalcul du salaire

Le salaire doit être recalculé selon les absences :

Cas :

#### Congé payé (`CONGE_PAYE`)

* Pas de réduction

#### Absence maladie (`ABSENCE_MALADIE`)

* Selon la règle métier (ex: payé ou partiellement payé)

#### Absence injustifiée (`ABSENCE_INJUSTIFIEE`)

* Déduction du nombre de jours absents

#### Congé exceptionnel (`CONGE_EXCEPTIONNEL`)

* Selon politique interne

Exemple :

```text
Salaire de base : 600000
3 jours d'absence injustifiée
Salaire journalier : 20000
Salaire final : 540000
```

---

## Fichiers à modifier

### Utilisateur.java

Compléter avec :

### Enum rôle

* `ADMIN`
* `VENDEUSE`
* `CUISINIER`
* `CHAUFFEUR`
* `REMPLACANT`

### Nouveaux champs

* `salaire_base_fixe`
* `statut_actif`

Exemple :

```java
@Enumerated(EnumType.STRING)
private Role role;

private Double salaireBaseFixe;

private Boolean statutActif;
```

---

## Fichiers à créer

### AbsenceConge

Créer :

* `AbsenceConge.java`
* `AbsenceCongeRepository.java`
* `AbsenceCongeService.java`
* `AbsenceCongeController.java`

---

## Structure de l'entité

### Champs

* `id_utilisateur` → FK vers `Utilisateur`

### Type d'absence (`type`)

* `CONGE_PAYE`
* `ABSENCE_MALADIE`
* `ABSENCE_INJUSTIFIEE`
* `CONGE_EXCEPTIONNEL`

### Dates

* `date_debut`
* `date_fin`

### Statut validation (`statut_validation`)

* `EN_ATTENTE`
* `VALIDE`
* `REFUSE`

Exemple :

```java
@ManyToOne
@JoinColumn(name = "id_utilisateur")
private Utilisateur utilisateur;

@Enumerated(EnumType.STRING)
private TypeAbsence type;

private LocalDate dateDebut;

private LocalDate dateFin;

@Enumerated(EnumType.STRING)
private StatutValidation statutValidation;
```

---

## Endpoints à créer

### Côté employé

#### Demander une absence

* `/absence/demander`

Permet de créer une nouvelle demande.

---

### Côté admin

#### Valider une absence

* `/absence/valider`

Paramètre :

* `id_absence`

Effet :

```text
statut_validation = VALIDE
```

---

#### Refuser une absence

* `/absence/refuser`

Paramètre :

* `id_absence`

Effet :

```text
statut_validation = REFUSE
```

---

### Consultation

* `/absence/find`
* `/absence/findAll`

---

## Affichage minimal

### Côté employé

### Formulaire : **Demander une absence**

Champs :

* Type d'absence (dropdown)
* Date début
* Date fin

Bouton :

**Envoyer**

---

## Côté admin

### Tableau des demandes en attente

Afficher :

* Employé
* Type
* Date début
* Date fin
* Statut

Exemple :

```text
Jean | CONGE_PAYE | 2026-07-01 | 2026-07-05 | EN_ATTENTE
```

Actions par ligne :

* Bouton **Valider**
* Bouton **Refuser**

---

## Gestion de l'inactivité

Dans la liste des employés :

Afficher :

* Nom
* Rôle
* Salaire
* Statut

Exemple :

```text
Paul | CHAUFFEUR | 500000 | ACTIF
Marc | CUISINIER | 450000 | INACTIF
```

Permet de conserver tout l'historique RH sans suppression physique.

# Tâches à accomplir

## Refactorisation des statistiques dashboard

>  **Back-end assigné : Miantsa**
>  **Front-end assigné : Giovan** — *Integration côté interface*

### Entités concernées

* `SessionTruck`
* `Commande`
* `Itineraire`

---

## Fonctionnalités à implémenter

### Statistiques de gains

Refactoriser le dashboard pour calculer les statistiques à partir des données de `SessionTruck` (journées de travail).

Permettre :

* Calcul du chiffre d'affaires **journalier**
* Calcul du chiffre d'affaires **hebdomadaire**
* Calcul du chiffre d'affaires **mensuel**

Le calcul doit être basé sur :

```text
SessionTruck → Commandes associées → Somme des montants
```

---

### Filtres avancés

Permettre de filtrer les statistiques :

#### Par date

Exemples :

* Une date précise
* Une plage de dates
* Une semaine
* Un mois

---

#### Par zone

Le filtre de zone doit se baser sur :

```text
SessionTruck → Itineraire → nom_zone
```

Exemple :

```text
Analakely
Ankorondrano
Ivandry
```

---

## Fichiers à modifier

Pas de nouvelle entité.

Modifier :

* `StatistiqueController.java`
* `StatistiqueService.java`

---

## Nouvelles méthodes à ajouter

### Chiffre d'affaires par session

Endpoint :

* `/chiffreAffaire/parSession`

Rôle :

Retourner la somme totale des commandes liées à une session donnée.

Paramètre :

* `id_session`

Logique :

```text
SELECT SUM(commande.total)
WHERE commande.id_session = ?
```

---

### Chiffre d'affaires par zone

Endpoint :

* `/chiffreAffaire/parZone`

Rôle :

Faire une jointure entre :

```text
SessionTruck → Itineraire → nom_zone
```

Puis sommer toutes les commandes de chaque session de cette zone.

Logique :

```text
SELECT zone, SUM(commande.total)
GROUP BY zone
```

---

## Requêtes à prévoir

### Statistiques hebdomadaires

Calcul :

* Grouper les sessions sur les 7 derniers jours
* Faire la somme des commandes

---

### Statistiques mensuelles

Calcul :

* Grouper les sessions du mois en cours
* Faire la somme des commandes

---

### Filtrage par intervalle de dates

Exemple :

```text
du 01/06/2026 au 30/06/2026
```

Permet de recalculer le CA uniquement sur la période choisie.

---

## Affichage minimal

### Tableau : Chiffre d'affaires par zone

Colonnes :

* Zone
* CA total

Exemple :

```text
Analakely      | 450000 Ar
Ankorondrano   | 380000 Ar
Ivandry        | 520000 Ar
```

---

## Réutilisation du graphe existant

Réutiliser le graphe du sprint 1 pour visualiser :

* CA par zone
* CA hebdomadaire
* CA mensuel

Visualisation possible :

* Histogramme
* Courbe
* Bar chart

Objectif :

Visualiser rapidement quelles zones performent le mieux.

---

## Objectif métier

Permettre :

* Une meilleure analyse des performances par zone
* Une meilleure analyse temporelle (jour/semaine/mois)
* Une meilleure prise de décision sur les itinéraires les plus rentables

# Tâches à accomplir

## Reçu / Facturation

> **Back-end assigné : Fifa**
> **Front-end assigné : Fabrice** — *Integration côté interface*

### Entités concernées

* `FactureRecu`

---

## Fonctionnalités à implémenter

À la fin d'une commande :

* Générer automatiquement un reçu/facture
* Associer le reçu à une commande
* Générer une référence unique de facture
* Enregistrer la date de facturation
* Enregistrer le mode de paiement utilisé

Le reçu doit être généré uniquement lorsque la commande est au statut :

```text
LIVREE
```

---

## Modes de paiement

Enum `mode_paiement`

* `ESPECE`
* `MOBILE_MONEY`

---

## Fichiers à créer

### FactureRecu

Créer :

* `FactureRecu.java`
* `FactureRecuRepository.java`
* `FactureRecuService.java`
* `FactureRecuController.java`

---

## Structure de l'entité

### Champs à ajouter

* `id_commande` → FK vers `Commande`
* `reference_facture`
* `date_facturation`
* `mode_paiement`

Exemple :

```java
@ManyToOne
@JoinColumn(name = "id_commande")
private Commande commande;

private String referenceFacture;

private LocalDateTime dateFacturation;

@Enumerated(EnumType.STRING)
private ModePaiement modePaiement;
```

---

## Génération automatique de référence

Dans `FactureRecuService.java`

Créer une méthode :

```java
genererReferenceFacture()
```

Format attendu :

```text
FACT-2026-00001
FACT-2026-00002
FACT-2026-00003
```

Structure :

```text
FACT-{ANNEE}-{NUMERO_INCREMENTAL}
```

Logique :

* Récupérer la dernière facture créée
* Incrémenter le compteur
* Générer la nouvelle référence

---

## Endpoints à créer

### Générer une facture

* `/facture/generer`

Paramètres :

* `id_commande`
* `mode_paiement`

Rôle :

* Vérifier que la commande est `LIVREE`
* Générer la référence
* Enregistrer la facture

Exemple :

```http
POST /facture/generer?id_commande=12&mode_paiement=ESPECE
```

---

### Consultation

* `/facture/find`
* `/facture/findAll`

---

## Affichage minimal

### Bouton de génération

Sur une commande avec statut :

```text
LIVREE
```

Afficher :

**Générer la facture**

---

## Formulaire de génération

Champs :

* Choix du mode de paiement

Options :

* ESPECE
* MOBILE_MONEY

Bouton :

**Valider**

---

## Affichage du reçu (texte brut)

Après génération :

Afficher une mini facture imprimable.

Exemple :

```text
==================================
           FOOD TRUCK
==================================
Référence : FACT-2026-00001
Date      : 23/06/2026 14:30
Commande  : #12
Montant   : 25 000 Ar
Paiement  : ESPECE
==================================
Merci pour votre achat !
==================================
```

---

## Objectif métier

Permettre :

* Une traçabilité complète des paiements
* Une gestion simple de la facturation
* Une impression rapide des reçus
* Un suivi des modes de paiement utilisés

---

# Récapitulatif des assignations — Sprint 2

| Tâche | Entité(s) | Back-end | Front-end |
|---|---|---|---|
| **Journée de travail du truck** | `Truck`, `SessionTruck` | Loïc | Nampoina |
| **Commande ↔ Session** | `Commande` | Fifa | Fabrice |
| **Personnalisation d'un plat** | `PersonnalisationCommande` | Fifa | Giovan |
| **Alerte stock & lots** | `LotIngredient` | Hasina | Nampoina (2ᵉ tâche) |
| **Profil employé & congés** | `Utilisateur`, `AbsenceConge` | Aro (Manotania ani hasina ra misy tsy azo) | Ismael |
| **Stats dashboard** | `SessionTruck`, `Commande`, `Itineraire` | Hasina | Giovan |
| **Facturation** | `FactureRecu` | Fifa | Fabrice  |

**Règle transverse à ne pas oublier :** toute liste/statistique/facture doit pouvoir s'exporter en **CSV et PDF** (pas d'import pour l'instant) — chaque dev back ajoute son propre endpoint d'export sur son module, pas besoin d'un ticket séparé.

**Point d'attention dépendances :** `Truck`/`SessionTruck` (Loïc) doit être livré en premier — `Commande` (Fifa) et les stats (Hasina) en dépendent. Prévoir une relecture rapide du modèle par Fifa ou Hasina avant que Loïc ne commence à coder.