# Sprint - 0:
- Deadline: Samedi 19h 00
- Duree: 3h 

## # Hasina : Module Dashboard Statistique

### Classes a creer:
    - StatistiqueController.java:
        * Contient @Controller
        /chiffreAffaire
        /benefice
        /benefice/idItineraire
        /...

        ( a partir de commande et itineraires )

    - StatistiqueService.java:
        * Cotier metier contenant lees @Transactional

### Fonctionnalites attendues:
 ?  1- Affichage des grand indicateurs ( titres )
    2- Affichage de la chiffre d'affaire globale: 
        -> en utilisant un API 
        -> pour recuperation de cette valeur (en Ar)
    3- Benefice totaux:  ( en Ar)
    4- Affichage ( graphe )
        -> on va utiliser une graphe
        ->  avec chart deja existant 
    5- Creation des donnees de test ( a mettre en dures )

==============================================

#### 1- Chiffre d'affaire globale 1h
- globalite:
    - Total des chiffres d'affaires 
    - Liste ( consultation )
        - Chiffres d'affairese / mois 
        - chiffres d'affaires / semaine
        - chiffres d'affaires / jour
    

- controller/StatistiqueController.java
    -  @GetMapping("/chiffreAffaire")
    -  public Double chiffreAffaire() -> StatistiqueService.getChiffreAffaireGlobal():

    * Methode:  getChiffreAffaireGlobal()
    - retourne la chiffre d'affaire global 
    - Tables necessaires:
        -> itineraires
        -> produit 
        -> commande
        -> ligneCommande
        -> personnalisationCommande
        -> factureRecu
            -> modePaiement
            -> 
    - Elements necessaires:
        -> 

Commnent ?
    - produit:
        * idProduit
        * prix de base
    - commande:
        * idCommande
        * montantTotal 
        * idStatutCommande=4 ( LIVREE)

    - ligneCommande:
        * idCommande
        * idProduit
        * quantite
        * prixUniraireFacture 

    - factureRecu:
        * idCommande
        * detailsTaxesBrut


Exemple: 
* Dans table produit:
P1      4000
P2      3000

* Dans table commande: 
Commande C1: (idCommande = 1)

* Dans table ligneCommande:
1       P1       1
1       P2       2

* factureRecu:
1       10000

-------------- TERMINE


#### 2- Benefice totaux: 1h
- globalite: 
    - Benefice globale 
    - Consultation:
        - Benefice / mois
        - Benefice / semaine
        - Benefice / jour
        Benefice = Recette - Depense 

    - filtre / itineraire 


    * Methode: getBeneficeTotal():
    - retourne la benefice globale
    - Tables necessaires:
        -> depense 
        -> chiffreAffaire 
    - Elemetns necessaires:
        ->

----------------- TERMINE

    * Methode: getBeneficeByItineraire( Long idItineraire ):
    - retourne la benefice par une itineraire
    - Tables necessaires:
        -> itineraire
            * idItineraire
            "heureDebutPrevue"
            "heureFinPrevue
            "jourSemaine"
        -> sessionTruck
            * idItineraire
            * fondDecaisseCloture
            * chiffreAffaireTotal
        -> depense
    - elements necessaires:
        -> 

itineraire.idItineraire
sessionTruck.idItineraire
beneficeByIditineraire = SUM ( ChiffreAffaireTotal ) dansla table sessionTruck pour un idItineraire - SUM ( montantDepense ) pour un idSession.iditineraire

- view: itineraire + sessionTruck + depense
iditineraire --------
idSession
idTruck
nomZone ------
dateSession
fondDeCasisseOuverture
fondDeCaisseCloture
ChiffreAffaireTotal ---------
montantDepense ---------- 
dateDepense ----------

beneficeByItineraire = SUM chiffreAffaireTotal - SUM montantDepense where idItineraire = idItineraire



#### 4- Donnee a afficher pour le graphe  a creer 2h 15
- globalite:

    Methode: getDonneesGraphique():
    - retourne la liste des donnee necessaires pour l'affichage de la graphe
    - Tables necessaires:
        ->
    - Elemetns necessaires: 
        -> 


===============================================
# Partie bloques: 
    1- Calcul de chiffres d'affaires globale:
    2- Calcul de Benefice Totaux:
        - Calcul de benefice par une itineraire
    3- Pour le graphe: on va afficher quoi >
        - Statistique par rapport a quoi ?

