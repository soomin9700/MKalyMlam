# Documentation technique — Module Commandes

## Vue d'ensemble

Ce document décrit la logique métier et technique implémentée pour la gestion des commandes
dans l'application MKalyMlam (Food Truck Management System).

---

## 1. Association automatique commande → session ouverte

### Principe

Chaque commande est obligatoirement liée à une **session de truck ouverte** (`SessionTruck`
avec statut `OUVERTE`). Cette liaison permet de tracer quel camion, quel itinéraire et quel
étaient les employés présents lors de la prise de commande.

### Flux technique

```
Utilisateur crée une commande
        │
        ▼
VenteService.ajouterCommande(commande, idTruck)
        │
        ├── 1. Recherche le Truck par idTruck
        │
        ├── 2. Recherche la session OUVERTE pour ce truck
        │      └─ SessionTruckRepository.findByTruck_IdTruckAndStatutSession_Libelle(idTruck, "OUVERTE")
        │      └─ Si aucune session trouvée → RuntimeException
        │
        ├── 3. Associe la session à la commande
        │      └─ commande.setSessionTruck(sessionOuverte)
        │
        ├── 4. Définit la date de création (now() si null)
        │
        ├── 5. Définit le statut par défaut : EN_ATTENTE
        │      └─ StatutCommandeRepository.findByLibelle("EN_ATTENTE")
        │
        ├── 6. Initialise montantTotal à 0.0
        │
        └── 7. Sauvegarde et retourne la commande
```

### Règles métier

- **Une seule session ouverte par truck** : la contrainte `existsByTruckAndStatutSession`
  dans `SessionTruckService.ouvrir()` empêche l'ouverture de deux sessions simultanées
  pour le même camion.
- **Pas de session ouverte = pas de commande** : si le truck n'a pas de session OUVERTE,
  la création de commande échoue avec une exception.
- **Le truck doit être DISPONIBLE** pour qu'une session puisse être ouverte.

### Code source

| Fichier | Méthode | Rôle |
|---------|---------|------|
| `VenteService.java` | `ajouterCommande()` | Logique principale d'association |
| `SessionTruckRepository.java` | `findByTruck_IdTruckAndStatutSession_Libelle()` | Requête JPQL pour trouver la session ouverte |
| `CommandeController.java` | `ajouter()` | Point d'entrée REST (`POST /commande/ajouter`) |

---

## 2. Gestion des changements de statut

### Statuts disponibles

La table `statutCommande` contient les valeurs suivantes (seedées dans `data.sql`) :

| ID | Libelle | Description |
|----|---------|-------------|
| 1 | `EN_ATTENTE` | Commande créée, en attente de traitement |
| 2 | `PREPARATION` | Commande en cours de préparation |
| 3 | `PRETE_POUR_RECUPERATION` | Commande prête, le client peut venir chercher |
| 4 | `LIVREE` | Commande livrée / remise au client |
| 5 | `ANNULEE` | Commande annulée |

### Flux de transition

```
EN_ATTENTE ──► PREPARATION ──► PRETE_POUR_RECUPERATION ──► LIVREE
     │                │                    │
     └──► ANNULEE ◄───┘────────────────────┘
```

### Implémentation technique

**Service** (`VenteService.changerStatut`) :
```java
@Transactional
public Commande changerStatut(Long idCommande, String nouveauStatutLibelle) {
    // 1. Charge la commande par ID
    // 2. Charge le nouveau statut par libellé (case-insensitive)
    // 3. Met à jour et sauvegarde
}
```

**Controller** (`CommandeController.changerStatut`) :
```
POST /commande/changerStatut?idCommande=1&statut=PREPARATION
```

**JSP** (dropdown dans `listeCommandes.jsp`) :
- Chaque ligne du tableau contient un `<select>` avec tous les statuts
- Le statut actuel est pré-sélectionné (`selected`)
- Un `onchange="this.form.submit()"` soumet automatiquement le formulaire
- Le formulaire POST vers `/commande/changerStatut`

### Règles métier

- **Pas de retour en arrière** : on ne peut pas repasser de `LIVREE` à `EN_ATTENTE`
  (le code le permet techniquement, mais l'UI guide l'utilisateur vers les transitions logiques)
- **Annulation possible à tout moment** : `ANNULEE` est accessible depuis n'importe quel statut
- **Validation côté serveur** : le service vérifie que le statut demandé existe en base

---

## 3. Vue liste des commandes (Tableau + Filtres)

### URL d'accès

```
GET /commande/liste
```

### Filtres disponibles

| Filtre | Paramètre | Type | Valeurs |
|--------|-----------|------|---------|
| Statut | `statut` | `<select>` | `Toutes`, `EN_ATTENTE`, `PREPARATION`, `PRETE_POUR_RECUPERATION`, `LIVREE`, `ANNULEE` |
| Type (mode) | `type` | `<select>` | `Tous`, `SUR_PLACE`, `A_DISTANCE` |

### Requêtes de filtrage

Le `CommandeRepository` expose des méthodes Spring Data JPA dérivées :

```java
// Toutes les commandes
List<Commande> findAll();

// Filtrer par statut
List<Commande> findByStatutCommande_Libelle(String libelle);

// Filtrer par type
List<Commande> findByTypeCommande_Libelle(String libelle);

// Filtrer par statut ET type
List<Commande> findByStatutCommande_LibelleAndTypeCommande_Libelle(String statut, String type);
```

### Logique de filtrage côté controller

```java
// CommandeListController.getListe()
if (statut != null && type != null) {
    // Filtre combiné
} else if (statut != null) {
    // Filtre par statut uniquement
} else if (type != null) {
    // Filtre par type uniquement
} else {
    // Pas de filtre → toutes les commandes
}
```

### Structure du tableau

| Colonne | Contenu | Notes |
|---------|---------|-------|
| # | `idCommande` | Badge numérique |
| Date | `dateHeureCreation` | Formaté `dd/MM/yyyy HH:mm` |
| Session | `sessionTruck.id` + itinéraire | Badge ID + sous-texte zone |
| Type | `typeCommande.libelle` | Badge coloré (SUR_PLACE / A_DISTANCE) |
| Montant | `montantTotal` | Formaté `€ XX.XX` |
| Statut | `statutCommande.libelle` | Badge coloré + dropdown pour changer |
| Actions | — | — |

### Codes couleurs des badges

**Statut :**
- `EN_ATTENTE` → Fond jaune `#FEF3C7`, texte `#92400E`
- `PREPARATION` → Fond bleu `#DBEAFE`, texte `#1E40AF`
- `PRETE_POUR_RECUPERATION` → Fond violet `#EDE9FE`, texte `#5B21B6`
- `LIVREE` → Fond vert `#D1FAE5`, texte `#065F46`
- `ANNULEE` → Fond rouge `#FEE2E2`, texte `#991B1B`

**Type :**
- `SUR_PLACE` → Fond vert `#D1FAE5`, texte `#065F46`
- `A_DISTANCE` → Fond orange `#FFEDD5`, texte `#9A3412`

---

## 4. Types de commande

### Valeurs seedées dans `data.sql`

| ID | Libelle | Description |
|----|---------|-------------|
| 1 | `SUR_PLACE` | Le client consomme sur place |
| 2 | `A_EMPORTER` | Le client emporte sa commande |
| 3 | `EN_LIGNE` | Commande passée en ligne |
| 4 | `A_DISTANCE` | Commande à distance (livraison) |

### Entité JPA

```java
@Entity
@Table(name = "typeCommande")
public class TypeCommande {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long idTypeCommande;
    private String libelle;
}
```

---

## 5. Fichiers modifiés / créés

### Fichiers modifiés

| Fichier | Modification |
|---------|-------------|
| `CommandeRepository.java` | Ajout des méthodes de filtrage JPQL |
| `VenteService.java` | Ajout de `listerCommandes()` et `listerCommandesFiltrees()` |
| `CommandeController.java` | Ajout de l'endpoint `changerStatut` |
| `data.sql` | Ajout de la valeur `A_DISTANCE` dans `typeCommande` |

### Fichiers créés

| Fichier | Rôle |
|---------|------|
| `CommandeListController.java` | Controller Spring MVC pour la vue liste (GET HTML) |
| `listeCommandes.jsp` | Vue JSP avec tableau, filtres et dropdown de statut |
| `comment.md` | Ce fichier de documentation |

---

## 6. Points d'attention

### Connexion entre REST et MVC

Le projet utilise deux patterns :
- **`@RestController`** (`CommandeController`) → endpoints JSON pour le JS côté client
- **`@Controller`** (`CommandeListController`) → endpoints HTML pour les vues JSP

La vue `listeCommandes.jsp` utilise le pattern `@Controller` avec `ModelAndView`
pour servir du HTML, tandis que le changement de statut est fait via un `@RestController`
en AJAX ou via un formulaire classique avec redirection.

### État transient de `sousTotal` dans `LigneCommande`

Le champ `sousTotal` est annoté `@Transient` — il n'est pas persisté en base.
Lors du rechargement depuis la DB, `sousTotal` vaut toujours `0.0`.
Le recalcul du montant total (`recalculerMontantCommande`) recalcule correctement
le total en sommant les `sousTotal` des lignes rechargées, mais ceux-ci sont à `0`.
**Solution** : recalculer en utilisant `prixUnitaireFacture * quantite` au lieu de `sousTotal`.

### N+1查询问题

La vue `listeCommandes.jsp` accède à `commande.statutCommande.libelle`,
`commande.typeCommande.libelle`, `commande.sessionTruck.id`, etc.
Avec `FetchType.LAZY` (défaut), chaque accès déclenche une requête SQL séparée.
Pour optimiser, il faudrait ajouter des requêtes `JOIN FETCH` dans le repository.
