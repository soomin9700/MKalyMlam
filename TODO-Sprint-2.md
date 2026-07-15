# TODOLIST - Sprint 2 - Gestion des equipements - Hasina
## Classes a creer: 
* `TypeMouvement.java`
    - idTypeMouvement
    - libelle
CREATE TABLE "typeMouvement" (
    "idTypeMouvement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

* `MouvementEquipement.java`
    - idMouvementEquipement
    - idTypeMouvement
    - idEquipement
    - quantite
    - dateMouvement
CREATE TABLE "mouvementEquipement" (
    "idMouvementEquipement" SERIAL PRIMARY KEY,
    "idTypeMouvement" INT NOT NULL,
    "idEquipement" INT NOT NULL,
    "quantite" NUMERIC(15,2) NOT NULL,
    "dateMouvement" DATE NOT NULL,

    FOREIGN KEY ("idTypeMouvement")
        REFERENCES "typeMouvement"("idTypeMouvement"),

    FOREIGN KEY ("idEquipement")
        REFERENCES "equipement"("idEquipement")
);

* `TypeEquipement.java`
    - idtypeEquipement
    - libelle
CREATE TABLE "typeEquipement" (
    "idTypeEquipement" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

* `MethodeComptable.java`
    - idMethodeComptable
    - libelle
CREATE TABLE "methodeComptable" (
    "idMethodeComptable" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

* `StatutAlerte.java`
    - idStatutAlerte
    - libelle
CREATE TABLE "statutAlerte" (
    "idStatutAlerte" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL
);

* `Equipement.java`
     "idEquipement" 
     "nomEquipement"
     "idTypeEquipement" 
     "idMethodeComptable"
     *prixUnitaire"
     *idStatutAlerte"
CREATE TABLE "equipement" (
    "idEquipement" SERIAL PRIMARY KEY,
    "nomEquipement" VARCHAR(100) NOT NULL,
    "idTypeEquipement" INT NOT NULL,
    "idMethodeComptable" INT NOT NULL,
    "prixUnitaire" NUMERIC(15,2) NOT NULL,
    "quantiteMin" NUMERIC(15,2) NOT NULL,

    FOREIGN KEY ("idTypeEquipement")
        REFERENCES "typeEquipement"("idTypeEquipement"),

    FOREIGN KEY ("idMethodeComptable")
        REFERENCES "methodeComptable"("idMethodeComptable")
);


* `EquipementRepository.java` ---ok

* `EquipementService.java` --ok

* `EquipementController.java` -- ok 
    * `/equipement/save`
    * `/equipement/update`
    * `/equipement/delete`
    * `/equipement/find`
    * `/equipement/findAll`
    * `/equipement/alertes`
        where idStatutAlerte = 2 or 3
    * `/equipement/calculerCump` — recalculer le CUMP après un nouvel achat


=========================================
## Fonctionnalites attendues: FINI

### Backend: 
[ok]1- Enregistrement d'un equipement
============

[ok] 2- Gestion de stock d'equipements: Ajout et Sortie ( MouvementEquipement)
    - Ajout ( entree ) des equipements 
    - Sortie ( cas d'utilisation )
============

[OK] 3- Calcul de la valeur du stock ( equipement ) selon la methode:
    - LIFO
    - CUMP
    

[ok] 4- Alerte pour chaque equipements: depend a la quantite totale d'un equipement dans la table 
============

[ok] 5- Export CSV
[ok] 6- Import CSV
============
=================================================


========================================
Ameliorations statistiques ( Sprint 0 )
========================================
ChiffreAffaireGlobal 
ChiffreAffaireParJour   
ChiffreAffaireParSemaine
ChiffreAffaireParMois
ChiffreAffaireByIdItineraire

DepenseGlobal
DepenseParJour
DepenseParSemaine
DepenseParMois
DpenseByIdItineraire

BenefieGlobal = ChiffreAffaireGlobal - DepenseGlobal 
BeneficeParJour = ChiffreAffaireParJour - DepenseParJour
BeneficeParSemaine = ChiffreAffaireParSemaine - DepenseParSemaine
BeneficeParMois = ChiffreAffaireParMois - DepenseParMois
BeneficeByIdItineraire = ChiffreAffaireByIdItineraire - DepenseByIdItineraire



===============================================
# RECAPITULATIF DES FICHIERS AJOUTES / MODIFIES
## fichiers:
- TODO-Sprint-2.md

## Base de données: 
- regarde le fichier `ajout-modif-table-hasina.sql` pour les nouvelles tables et modifications

## Entity:
    - Equipement.java
    - TypeEquipement.java
    - MethodeComptable.java
    - TypeMouvement.java
    - MouvementEquipement.java

## Controller:
    - EquipementController.java
    - TypeMouvementController.java
    - MouvementEquipementController.java
    - MethodeComptableController.java
    - TypeEquipementController.java

## Repository:
    - EquipementRepository.java
    - TypeMouvementRepository.java
    - MouvementEquipementRepository.java
    - MethodeComptableRepository.java
    - TypeEquipementRepository.java

## Service:
    - EquipementService.java
    - TypeMouvementService.java
    - MouvementEquipementService.java
    - MethodeComptableService.java
    - TypeEquipementService.java
    - ImportService.java
    - ExportService.java

## view 
    - equipement/form.jsp
    - equipement/equipementsAlertes.jsp

    - mouvementEquipement/form.jsp
    - mouvementEquipement/etatStock.jsp
    


