package com.mkalymlam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.models.Commande;
import com.mkalymlam.models.LigneCommande;
import com.mkalymlam.models.Produit;
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

    public Commande ajouterCommande(Commande commande) {
        commande.setMontantTotal(0);
        return commandeRepository.save(commande);
    }

    public LigneCommande ajouterLigneCommande(LigneCommande ligne) {
        Produit produit = produitRepository.findById(ligne.getIdProduit())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        double sousTotal = produit.getPrixBase() * ligne.getQuantite();
        ligne.setSousTotal(sousTotal);
        LigneCommande saved = ligneCommandeRepository.save(ligne);

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
}
