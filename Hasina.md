# Résumé — Fichiers LotIngredient

Ce document regroupe les fichiers Java et les vues JSP liés au module "LotIngredient".

## Fichiers Java
- `src/main/java/com/mkalymlam/entity/LotIngredient.java` : Entité JPA représentant un lot d'ingrédient (id, ingrédient, dates, quantités, prix).
- `src/main/java/com/mkalymlam/repository/LotIngredientRepository.java` : Interface Spring Data JPA pour les requêtes sur `LotIngredient` (recherche, sommes, filtres).
- `src/main/java/com/mkalymlam/service/LotIngredientService.java` : Logique métier pour gérer les lots (lecture, filtres, calculs de stock, save/update/delete).
- `src/main/java/com/mkalymlam/controller/LotIngredientController.java` : Contrôleur MVC exposant les routes pour afficher les vues (`/lot/findAll`, `/lot/api`, `/lot/save`, endpoints pour périmés/alertes, etc.).
- `src/test/java/com/mkalymlam/controller/LotIngredientControllerTest.java` : Tests unitaires/integration du contrôleur.
- `src/test/java/com/mkalymlam/service/LotIngredientServiceTest.java` : Tests unitaires du service.

## Vues JSP (formulaires, listes, pages d'alerte/périmés)
- `src/main/webapp/WEB-INF/views/lot/form.jsp` : Formulaire d'édition/création d'un lot (utilisé par la vue `lot`).
- `src/main/webapp/WEB-INF/views/lot/list.jsp` : Liste principale des lots affichée dans le module `lot` (tableau, filtres, badges d'état).
- `src/main/webapp/WEB-INF/views/lotIngredien/form.jsp` : Vue ajoutée pour LotIngredient (formulaire + tableau d'affichage intégré).
- `src/main/webapp/WEB-INF/views/ingredient/lotIngredientForm.jsp` : Formulaire et liste combinés pour la gestion des lots d'ingrédients (filtres, création, affichage).
- `src/main/webapp/WEB-INF/views/ingredient/ingredient-bientot-perimes.jsp` : Page listant les lots bientôt périmés.
- `src/main/webapp/WEB-INF/views/ingredient/ingredient-bientot-perimes-by-idIngredient.jsp` : Variation filtrée par ingrédient.
- `src/main/webapp/WEB-INF/views/ingredient/ingredient-perimes.jsp` : Page listant les lots déjà périmés.
- `src/main/webapp/WEB-INF/views/ingredient/ingredient-alertes.jsp` : Page affichant les ingrédients en alerte selon le seuil.

---

Si tu veux, j'ajoute pour chaque fichier :
- les routes HTTP exactes (dans le contrôleur),
- les extraits de JSP montrant les champs importants,
- ou un plan de migration si tu veux réintroduire `quantiteRestante` dans la base.

Dis-moi ce que tu préfères ensuite.