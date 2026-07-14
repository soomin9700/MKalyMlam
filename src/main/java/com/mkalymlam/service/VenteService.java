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
    private final ConsommationService consommationService;
    private final HistoriqueStatutCommandeRepository historiqueStatutCommandeRepository;
    // private final NotificationService notificationService;

    public VenteService(CommandeRepository commandeRepository,
                        LigneCommandeRepository ligneCommandeRepository,
                        ProduitRepository produitRepository,
                        SessionTruckRepository sessionTruckRepository,
                        StatutCommandeRepository statutCommandeRepository,
                        TruckRepository truckRepository,
                        ConsommationService consommationService,
                        HistoriqueStatutCommandeRepository historiqueStatutCommandeRepository/*,
    NotificationService notificationService*/) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.statutCommandeRepository = statutCommandeRepository;
        this.truckRepository = truckRepository;
        this.consommationService = consommationService;
        this.historiqueStatutCommandeRepository = historiqueStatutCommandeRepository;
        // this.notificationService = notificationService;
    }
    
    @Transactional
    public Commande ajouterCommande(Commande commande, Long idTruck) {
        final Long truckId;
        if (idTruck != null) {
            truckId = idTruck;
        } else {
            List<SessionTruck> sessionsOuvertes = sessionTruckRepository.findByStatutSession_Libelle("OUVERTE");
            if (sessionsOuvertes.isEmpty()) {
                throw new RuntimeException("Aucune session ouverte disponible");
            }
            truckId = sessionsOuvertes.get(0).getTruck().getId();
        }

        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new RuntimeException("Truck introuvable avec l'id : " + truckId));
        
        SessionTruck sessionOuverte = sessionTruckRepository
                .findByTruck_IdAndStatutSession_Libelle(truckId, "OUVERTE")
                .orElseThrow(() -> new RuntimeException("Aucune session ouverte pour le truck " + truckId));
        
        commande.setSessionTruck(sessionOuverte);
        
        if (commande.getDateHeureCreation() == null) {
            commande.setDateHeureCreation(LocalDateTime.now());
        }
        
        StatutCommande statutEnAttente = statutCommandeRepository.findByLibelle("EN_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Le statut EN_ATTENTE n'existe pas en base."));
        commande.setStatutCommande(statutEnAttente);
        
        if (commande.getMontantTotal() == 0) {
            commande.setMontantTotal(0.0);
        }
        
        return commandeRepository.save(commande);
    }
    
    @Transactional
    public Commande changerStatut(Long idCommande, String nouveauStatutLibelle) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'id : " + idCommande));
        
        StatutCommande nouveauStatut = statutCommandeRepository.findByLibelle(nouveauStatutLibelle.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Statut invalide : " + nouveauStatutLibelle));
        
        String ancienStatut = commande.getStatutCommande() != null ? commande.getStatutCommande().getLibelle() : null;
        commande.setStatutCommande(nouveauStatut);
        Commande saved = commandeRepository.save(commande);
        
        enregistrerHistoriqueStatut(saved, ancienStatut, nouveauStatutLibelle);

        // if ("ANNULEE".equals(nouveauStatutLibelle.toUpperCase())) {
        //     notificationService.notifierCommandeAnnulee(saved);
        // }
        // notificationService.notifierHeureRecuperation(saved);
        
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

        if (commande.getSessionTruck() != null) {
            consommationService.deduireIngredients(idCommande, commande.getSessionTruck().getId());
        }

        String ancienStatut = commande.getStatutCommande() != null ? commande.getStatutCommande().getLibelle() : null;
        StatutCommande statutPreparation = statutCommandeRepository.findByLibelle("PREPARATION")
                .orElseThrow(() -> new RuntimeException("Le statut PREPARATION n'existe pas en base."));
        commande.setStatutCommande(statutPreparation);
        Commande saved = commandeRepository.save(commande);

        enregistrerHistoriqueStatut(saved, ancienStatut, "PREPARATION");
        
        return saved;
    }

    @Transactional
    public Commande annulerCommande(Long idCommande) {
        Commande commande = getCommande(idCommande);
        String statutActuel = commande.getStatutCommande() != null ? commande.getStatutCommande().getLibelle() : null;

        if ("ANNULEE".equals(statutActuel) || "LIVREE".equals(statutActuel)) {
            throw new RuntimeException("Impossible d'annuler une commande déjà " + statutActuel);
        }

        if ("PREPARATION".equals(statutActuel) || "PRETE_POUR_RECUPERATION".equals(statutActuel)) {
            if (commande.getSessionTruck() != null) {
                consommationService.remettreEnStock(idCommande, commande.getSessionTruck().getId());
            }
        }

        StatutCommande statutAnnulee = statutCommandeRepository.findByLibelle("ANNULEE")
                .orElseThrow(() -> new RuntimeException("Le statut ANNULEE n'existe pas en base."));
        commande.setStatutCommande(statutAnnulee);
        Commande saved = commandeRepository.save(commande);

        enregistrerHistoriqueStatut(saved, statutActuel, "ANNULEE");
        
        return saved;
    }

    @Transactional
    public Commande cloturerCommande(Long idCommande) {
        Commande commande = getCommande(idCommande);
        String statutActuel = commande.getStatutCommande() != null ? commande.getStatutCommande().getLibelle() : null;

        if (!"PRETE_POUR_RECUPERATION".equals(statutActuel) && !"LIVREE".equals(statutActuel)) {
            throw new RuntimeException("La commande doit être PRETE_POUR_RECUPERATION ou LIVREE pour être clôturée");
        }

        StatutCommande statutLivree = statutCommandeRepository.findByLibelle("LIVREE")
                .orElseThrow(() -> new RuntimeException("Le statut LIVREE n'existe pas en base."));
        commande.setStatutCommande(statutLivree);
        Commande saved = commandeRepository.save(commande);

        enregistrerHistoriqueStatut(saved, statutActuel, "LIVREE");
        
        return saved;
    }

    @Transactional
    public Commande refuserCommande(Long idCommande) {
        Commande commande = getCommande(idCommande);
        String statutActuel = commande.getStatutCommande() != null ? commande.getStatutCommande().getLibelle() : null;

        if ("ANNULEE".equals(statutActuel) || "LIVREE".equals(statutActuel)) {
            throw new RuntimeException("Impossible de refuser une commande déjà " + statutActuel);
        }

        if ("PREPARATION".equals(statutActuel)) {
            if (commande.getSessionTruck() != null) {
                consommationService.remettreEnStock(idCommande, commande.getSessionTruck().getId());
            }
        }

        StatutCommande statutAnnulee = statutCommandeRepository.findByLibelle("ANNULEE")
                .orElseThrow(() -> new RuntimeException("Le statut ANNULEE n'existe pas en base."));
        commande.setStatutCommande(statutAnnulee);
        Commande saved = commandeRepository.save(commande);

        enregistrerHistoriqueStatut(saved, statutActuel, "ANNULEE");
        
        return saved;
    }
    
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public List<Commande> rechercherCommandes(String recherche) {
        if (recherche == null || recherche.trim().isEmpty()) {
            return commandeRepository.findAll();
        }
        String r = recherche.trim();
        try {
            Long id = Long.parseLong(r);
            return commandeRepository.findByIdCommande(id)
                    .map(List::of)
                    .orElse(List.of());
        } catch (NumberFormatException e) {
            return commandeRepository.findAll();
        }
    }

    public List<Commande> listerCommandesFiltrees(String statut, String type) {
        boolean hasStatut = statut != null && !statut.isEmpty();
        boolean hasType = type != null && !type.isEmpty();

        if (hasStatut && hasType) {
            return commandeRepository.findByStatutCommande_LibelleAndTypeCommande_Libelle(statut, type);
        } else if (hasStatut) {
            return commandeRepository.findByStatutCommande_Libelle(statut);
        } else if (hasType) {
            return commandeRepository.findByTypeCommande_Libelle(type);
        }
        return commandeRepository.findAll();
    }

    public List<Commande> listerVentes(LocalDateTime dateDebut, LocalDateTime dateFin, Long idSession, String zone) {
        return commandeRepository.findVentesFiltrees(dateDebut, dateFin, idSession, zone);
    }

    public List<HistoriqueStatutCommande> getHistoriqueStatut(Long idCommande) {
        return historiqueStatutCommandeRepository.findByCommande_IdCommandeOrderByDateChangementDesc(idCommande);
    }
    
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
    
    public Commande getCommande(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
    }
    
    public double getMontantCommande(Long id) {
        return getCommande(id).getMontantTotal();
    }
    
    public List<LigneCommande> getLignesByCommande(Long idCommande) {
        return ligneCommandeRepository.findByIdCommande(idCommande);
    }
    
    private void recalculerMontantCommande(Long idCommande) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByIdCommande(idCommande);
        double total = lignes.stream()
                .mapToDouble(l -> l.getPrixUnitaireFacture() * l.getQuantite())
                .sum();
        Commande commande = getCommande(idCommande);
        commande.setMontantTotal(total);
        commandeRepository.save(commande);
    }

    public double getMontantLignes(Long idCommande) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByIdCommande(idCommande);
        return lignes.stream()
                .mapToDouble(l -> l.getPrixUnitaireFacture() * l.getQuantite())
                .sum();
    }

    private void enregistrerHistoriqueStatut(Commande commande, String ancienStatut, String nouveauStatut) {
        HistoriqueStatutCommande historique = new HistoriqueStatutCommande(commande, ancienStatut, nouveauStatut);
        historiqueStatutCommandeRepository.save(historique);
    }
}
