package com.mkalymlam.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Commande;
import com.mkalymlam.entity.LigneCommande;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.repository.CommandeRepository;
import com.mkalymlam.repository.LigneCommandeRepository;
import com.mkalymlam.repository.ProduitRepository;

@Service
public class VenteService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;

    public VenteService(CommandeRepository commandeRepository,
            LigneCommandeRepository ligneCommandeRepository,
            ProduitRepository produitRepository) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
    }

    // Crée une commande initiale vide
    public Commande ajouterCommande(Commande commande) {
        commande.setMontantTotal(0.0);
        return commandeRepository.save(commande);
    }

    // Ajoute une ligne de produit au panier
    public LigneCommande ajouterLigneCommande(LigneCommande ligne) {
        // Ici, ligne.getIdProduit() est un Long. ProduitRepository attend un Long. Zéro
        // erreur.
        Produit produit = produitRepository.findById(ligne.getIdProduit())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        // Sécurise la contrainte NOT NULL de prixunitairefacture
        ligne.setPrixUnitaireFacture(produit.getPrixBase());

        // Calcule le sous-total
        double sousTotal = produit.getPrixBase() * ligne.getQuantite();
        ligne.setSousTotal(sousTotal);

        // Sauvegarde de la ligne
        LigneCommande saved = ligneCommandeRepository.save(ligne);

        // Recalcule le prix de la commande parente
        recalculerMontantCommande(ligne.getIdCommande());

        return saved;
    }

    public double getMontantLignes(Long idCommande) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByIdCommande(idCommande);
        return lignes.stream().mapToDouble(LigneCommande::getSousTotal).sum();
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
        double total = getMontantLignes(idCommande);
        Commande commande = getCommande(idCommande);
        commande.setMontantTotal(total);
        commandeRepository.save(commande);
    }

    public Commande findById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec ID : " + id));
    }

    public void save(Commande commande) {
        commandeRepository.save(commande);
    }
}