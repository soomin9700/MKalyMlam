package com.mkalymlam.service;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VenteService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;
    private final SessionTruckRepository sessionTruckRepository;
    private final StatutCommandeRepository statutCommandeRepository;
    private final TruckRepository truckRepository;

    public VenteService(CommandeRepository commandeRepository,
                        LigneCommandeRepository ligneCommandeRepository,
                        ProduitRepository produitRepository,
                        SessionTruckRepository sessionTruckRepository,
                        StatutCommandeRepository statutCommandeRepository,
                        TruckRepository truckRepository) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.statutCommandeRepository = statutCommandeRepository;
        this.truckRepository = truckRepository;
    }
    
    // Ajouter une nouvelle commande
    @Transactional
    public Commande ajouterCommande(Commande commande, Long idTruck) {
        
        // 1. Récupérer le truck
        Truck truck = truckRepository.findById(idTruck)
                .orElseThrow(() -> new RuntimeException("Truck introuvable avec l'id : " + idTruck));
        
        // 2. Récupérer la session ouverte de ce truck
        SessionTruck sessionOuverte = sessionTruckRepository
                .findByTruck_IdTruckAndStatutSession_Libelle(idTruck, "OUVERTE")
                .orElseThrow(() -> new RuntimeException("Aucune session ouverte pour le truck " + idTruck));
        
        // 3. Associer la session à la commande
        commande.setSessionTruck(sessionOuverte);
        
        // 4. Mettre la date de création si elle n'est pas renseignée
        if (commande.getDateHeureCreation() == null) {
            commande.setDateHeureCreation(LocalDateTime.now());
        }
        
        // 5. Mettre un statut par défaut : EN_ATTENTE
        StatutCommande statutEnAttente = statutCommandeRepository.findByLibelle("EN_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Le statut EN_ATTENTE n'existe pas en base."));
        commande.setStatutCommande(statutEnAttente);
        
        // 6. Initialiser le montant total à 0 si pas défini
        if (commande.getMontantTotal() == null) {
            commande.setMontantTotal(0.0);
        }
        
        // 7. Sauvegarder la commande
        return commandeRepository.save(commande);
    }
    
    // Changer le statut d'une commande (NOUVEAU, demandé dans le taf)
    @Transactional
    public Commande changerStatut(Long idCommande, String nouveauStatutLibelle) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'id : " + idCommande));
        
        StatutCommande nouveauStatut = statutCommandeRepository.findByLibelle(nouveauStatutLibelle.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Statut invalide : " + nouveauStatutLibelle));
        
        commande.setStatutCommande(nouveauStatut);
        
        return commandeRepository.save(commande);
    }
    
    // Récupérer toutes les commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
    
    // Le reste de tes méthodes (ajouterLigneCommande, validerCommande, etc.)
    
    public LigneCommande ajouterLigneCommande(LigneCommande ligne) {
        Produit produit = produitRepository.findById(ligne.getIdProduit())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        
        double montantLigne = produit.getPrixBase() * ligne.getQuantite();
        ligne.setPrixUnitaireFacture(produit.getPrixBase());
        ligne.setSousTotal(montantLigne);
        
        LigneCommande saved = ligneCommandeRepository.save(ligne);
        recalculerMontantCommande(ligne.getIdCommande());
        return saved;
    }
    
    @Transactional
    public Commande validerCommande(Long idCommande, List<LigneCommande> lignes) {
        Commande commande = getCommande(idCommande);
        
        double total = 0.0;
        for (LigneCommande ligne : lignes) {
            Produit produit = produitRepository.findById(ligne.getIdProduit())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable"));
            ligne.setIdCommande(idCommande);
            double prixUnitaire = produit.getPrixBase();
            double montantLigne = prixUnitaire * ligne.getQuantite();
            ligne.setPrixUnitaireFacture(prixUnitaire);
            ligne.setSousTotal(montantLigne);
            ligneCommandeRepository.save(ligne);
            total += montantLigne;
        }
        
        commande.setMontantTotal(total);
        return commandeRepository.save(commande);
    }
    
    public Commande getCommande(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
    }
    
    public double getMontantCommande(Long id) {
        return getCommande(id).getMontantTotal() != null ? getCommande(id).getMontantTotal() : 0.0;
    }
    
    public List<LigneCommande> getLignesByCommande(Long idCommande) {
        return ligneCommandeRepository.findByIdCommande(idCommande);
    }
    
    private void recalculerMontantCommande(Long idCommande) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByIdCommande(idCommande);
        double total = lignes.stream()
                .mapToDouble(l -> l.getSousTotal() != null ? l.getSousTotal() : 0.0)
                .sum();
        Commande commande = getCommande(idCommande);
        commande.setMontantTotal(total);
        commandeRepository.save(commande);
    }
}